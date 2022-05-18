package com.learn.tinhtoan;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import com.learn.tinhtoan.activity.LoginActivity;
import com.learn.tinhtoan.model.DataUser;
import com.learn.tinhtoan.model.User;
import com.learn.tinhtoan.model.UserAchievement;
import com.learn.tinhtoan.model.UserProfile;

import static com.learn.tinhtoan.activity.LoginActivity.database;

public class Database extends SQLiteOpenHelper{

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
    public static Cursor findUserName(String username){
        return LoginActivity.database.getData("SELECT * FROM User WHERE Username = '" + username + "'");
    }

    //ham tim id cua username
    public static int findUserId(String name) {
        Cursor cursor = LoginActivity.database.getData("SELECT * FROM User WHERE Username = '" + name + "'");
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    //ham tim data user
    public static Cursor findUserData(int id){
        return LoginActivity.database.getData("SELECT * FROM UserData WHERE UserId = " + id);
    }

    //ham tim user achievement
    public static Cursor findUserAchievement(int id){
        return LoginActivity.database.getData("SELECT * FROM UserAchievement WHERE UserId = " + id);
    }

    //ham tim user profile
    public static Cursor findUserProfile(int id){
        return LoginActivity.database.getData("SELECT * FROM UserProfile WHERE UserId = " + id);
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
    public static void updateUserData(DataUser dataUser){
        String sql = "UPDATE UserData SET Diem = " + dataUser.getDiem() + ", SoLanTinhToan = " + dataUser.getSoLanTinhToan() +
                ", SoCauTraLoi = " + dataUser.getSoCauTraLoi() + ", SoCauDung = " + dataUser.getSoCauDung() +
                ", SoCauCong = " + dataUser.getSoCauCong() + ", SoCauTru = " + dataUser.getSoCauTru() +
                ", SoCauNhan = " + dataUser.getSoCauNhan() + ", SoCauChia = " + dataUser.getSoCauChia() +
                ", SoCauDe = " + dataUser.getSoCauDe() + ", SoCauKho = " + dataUser.getSoCauKho() +
                ", SoCauTB = " + dataUser.getSoCauTB() +
                " WHERE UserId = " + dataUser.getUserId() + ";";
        database.queryData(sql);
    }

    //update profile
    public static void updateUserProfile(UserProfile userProfile){
        String sql = "UPDATE UserProfile SET fullname = \"" + userProfile.getFullname() + "\"" +
                ", date_of_birth = " + "\"" + userProfile.getDateOfBirth() + "\"" +
                ", gender = " + userProfile.getGender() + ", email = \""  + userProfile.getEmail() + "\"" +
                ", address = \"" + userProfile.getAddress() + "\"" + ", phone = \"" + userProfile.getPhone() + "\"" +
                " WHERE UserId = " + userProfile.getUserId() + ";";
        database.queryData(sql);
    }

    public static void updateUserAchievement(UserAchievement userAchievement) {
        String sql = "UPDATE UserAchievement SET level = " + userAchievement.getLevel() +
                ", title = \"" + userAchievement.getTitle() + "\", dung_het = " + userAchievement.getDungHet() +
                ", sai_het = " + userAchievement.getSaiHet() + ", am_diem = " + userAchievement.getAmDiem() +
                ", clear_hard = " + userAchievement.getClearHard() + ", clear_operators = " + userAchievement.getClearOperators() +
                ", clear_add = " + userAchievement.getClearAdd() + ", clear_sub = " + userAchievement.getClearSub() +
                ", clear_mul = " + userAchievement.getClearMul() + ", clear_div = " + userAchievement.getClearDiv() +
                ", under_15_seconds = " + userAchievement.getUnder15Seconds() +
                ", clear_minigames = " + userAchievement.getClearMinigames() +
                " WHERE UserId = " + userAchievement.getUserId() + ";";
        database.queryData(sql);
    }

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
