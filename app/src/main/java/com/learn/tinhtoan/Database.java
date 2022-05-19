package com.learn.tinhtoan;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import com.learn.tinhtoan.model.DataUser;
import com.learn.tinhtoan.model.User;
import com.learn.tinhtoan.model.UserAchievement;
import com.learn.tinhtoan.model.UserProfile;

public class Database extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "TinhToan.sqlite";
    private static int DATABASE_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS User(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Username VARCHAR(150) NOT NULL, Password VARCHAR(150) NOT NULL, Image BLOB)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS UserData(UserId INTEGER PRIMARY KEY, " +
                "Diem INTEGER NOT NULL, SoCauTraLoi INTEGER NOT NULL, SoCauDung INTEGER NOT NULL," +
                " SoCauCong INTEGER NOT NULL, SoCauTru INTEGER NOT NULL, SoCauNhan INTEGER NOT NULL," +
                " SoCauDe INTEGER NOT NULL, SoCauTB INTEGER NOT NULL, SoCauKho INTEGER NOT NULL," +
                " SoCauChia INTEGER NOT NULL, SoLanTinhToan INTEGER NOT NULL, SoLanMiniGame INTEGER NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS UserAchievement(UserId INTEGER PRIMARY KEY, " +
                "level INTEGER NOT NULL, title NVACHAR(100) NOT NULL, dung_het INTEGER NOT NULL," +
                " sai_het INTEGER NOT NULL, am_diem INTEGER NOT NULL, clear_hard INTEGER NOT NULL, clear_operators INTEGER NOT NULL," +
                " under_15_seconds INTEGER NOT NULL, clear_add INTEGER NOT NULL, clear_sub INTEGER NOT NULL," +
                " clear_mul INTEGER NOT NULL, clear_div INTEGER NOT NULL, clear_minigames INTEGER NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS UserProfile(UserId INTEGER PRIMARY KEY, " +
                " fullname NVACHAR(100), date_of_birth VARCHAR(20), gender INTEGER," +
                " email VARCHAR(100), phone VARCHAR(20), address NVARCHAR(200))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Task(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER NOT NULL, content NVARCHAR(200) NOT NULL, status INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public void addUser(String name, String password, byte[] hinh){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO User VALUES(null, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, password);
        statement.bindBlob(3, hinh);

        statement.executeInsert();
    }

    public void addUserData(int UserId){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO UserData VALUES(" + UserId + ", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)";
        database.execSQL(sql);
    }

    public void addUserAchievement(int UserId){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO UserAchievement VALUES(" + UserId + ", 1, \"Tập sự\", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)";
        database.execSQL(sql);
    }

    public void addUserProfile(int UserId){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO UserProfile(UserId, gender) VALUES(" + UserId + ", 0)";
        database.execSQL(sql);
    }

    //ham tim username
    public Cursor findUserName(String username){
        return getData("SELECT * FROM User WHERE Username = '" + username + "'");
    }

    //ham tim id cua username
    public int findUserId(String name) {
        Cursor cursor = getData("SELECT * FROM User WHERE Username = '" + name + "'");
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    //ham tim data user
    public Cursor findUserData(int id){
        return getData("SELECT * FROM UserData WHERE UserId = " + id);
    }

    //ham tim user achievement
    public Cursor findUserAchievement(int id){
        return getData("SELECT * FROM UserAchievement WHERE UserId = " + id);
    }

    //ham tim user profile
    public Cursor findUserProfile(int id){
        return getData("SELECT * FROM UserProfile WHERE UserId = " + id);
    }

    //update user
    public void updateUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cvImage = new ContentValues();
        ContentValues cvPassword = new ContentValues();
        cvImage.put("Image", user.getImage());
        cvPassword.put("Password",user.getPassword());
//        String sql = "UPDATE User SET Password = \""  + user.getPassword() + "\"" + " WHERE UserId = " + user.getId() + ";";
//        db.execSQL(sql);
        db.update("User", cvPassword, "Id = " + user.getId(), null);
        db.update("User", cvImage, "Id = " + user.getId(), null);
    }

    //update data
    public void updateUserData(DataUser dataUser){
        String sql = "UPDATE UserData SET Diem = " + dataUser.getDiem() + ", SoLanTinhToan = " + dataUser.getSoLanTinhToan() +
                ", SoCauTraLoi = " + dataUser.getSoCauTraLoi() + ", SoCauDung = " + dataUser.getSoCauDung() +
                ", SoCauCong = " + dataUser.getSoCauCong() + ", SoCauTru = " + dataUser.getSoCauTru() +
                ", SoCauNhan = " + dataUser.getSoCauNhan() + ", SoCauChia = " + dataUser.getSoCauChia() +
                ", SoCauDe = " + dataUser.getSoCauDe() + ", SoCauKho = " + dataUser.getSoCauKho() +
                ", SoCauTB = " + dataUser.getSoCauTB() +
                " WHERE UserId = " + dataUser.getUserId() + ";";
        queryData(sql);
    }

    //update profile
    public void updateUserProfile(UserProfile userProfile){
        String sql = "UPDATE UserProfile SET fullname = \"" + userProfile.getFullname() + "\"" +
                ", date_of_birth = " + "\"" + userProfile.getDateOfBirth() + "\"" +
                ", gender = " + userProfile.getGender() + ", email = \""  + userProfile.getEmail() + "\"" +
                ", address = \"" + userProfile.getAddress() + "\"" + ", phone = \"" + userProfile.getPhone() + "\"" +
                " WHERE UserId = " + userProfile.getUserId() + ";";
        queryData(sql);
    }

    public void updateUserAchievement(UserAchievement userAchievement) {
        String sql = "UPDATE UserAchievement SET level = " + userAchievement.getLevel() +
                ", title = \"" + userAchievement.getTitle() + "\", dung_het = " + userAchievement.getDungHet() +
                ", sai_het = " + userAchievement.getSaiHet() + ", am_diem = " + userAchievement.getAmDiem() +
                ", clear_hard = " + userAchievement.getClearHard() + ", clear_operators = " + userAchievement.getClearOperators() +
                ", clear_add = " + userAchievement.getClearAdd() + ", clear_sub = " + userAchievement.getClearSub() +
                ", clear_mul = " + userAchievement.getClearMul() + ", clear_div = " + userAchievement.getClearDiv() +
                ", under_15_seconds = " + userAchievement.getUnder15Seconds() +
                ", clear_minigames = " + userAchievement.getClearMinigames() +
                " WHERE UserId = " + userAchievement.getUserId() + ";";
        queryData(sql);
    }

}
