package com.danielsoto.coolstuff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    public DatabaseHelper(@Nullable Context context){
        super(context, "name.db", null, 21);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists User (id integer primary key autoincrement, username text, passsword text)");
    }
    @Override
    public void  onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists User");
        onCreate(db);
    }
    public long registerUser(Data data) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", data.getUsername());
        contentValues.put("password", data.getPassword());
        long user = sqLiteDatabase.insert("User", null, contentValues);
        if(user != 1) {
            Log.e(TAG, "registerUser: User register successfully");
        } else {
            Log.e(TAG, "registerUser: error in register user...");
        }
        sqLiteDatabase.close();
        return user;
    }
    public void loginUser(Data data){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from User", null, null);
        while (cursor.moveToNext()){
            String username = cursor.getString(1);
            String password = cursor.getString(2);

            if(username.equals(data.getUsername()) && password.equals(data.getPassword())) {
                Log.e(TAG, "LoginUser: User Login successfully");
            } else {
                Log.e(TAG, "LoginUser: Error in login user...");
            }
        }
        cursor.close();
        sqLiteDatabase.close();
    }
}
