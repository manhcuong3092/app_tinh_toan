package com.learn.tinhtoan;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import static com.learn.tinhtoan.LoginActivity.database;

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

    public void addUserData(int idUser){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO DataUser VALUES(" + idUser + ", 0, 0, 0, 0, 0, 0, 0)";
        database.execSQL(sql);
    }

    //ham tim username
    public static Cursor findUserName(String username){
        return LoginActivity.database.getData("SELECT * FROM User WHERE Username = '" + username + "'");
    }

    //ham tim id cua username
    public static int findIdUser(String name) {
        Cursor cursor = LoginActivity.database.getData("SELECT * FROM User WHERE Username = '" + name + "'");
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    //ham tim username
    public static Cursor findUserData(int id){
        return LoginActivity.database.getData("SELECT * FROM DataUser WHERE IdUser = " + id);
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
