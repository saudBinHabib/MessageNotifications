package com.example.saadi.message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Saadi on 22-Dec-17.
 */

public class UserDb extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "USERINFO.DB";
    public  static final String CREATE_QUERY = "CREATE TABLE "+ UserContract.NewUserInfo.TABLE_NAME+"("+ UserContract.NewUserInfo.USER_NAME+"" +
            " TEXT,"+ UserContract.NewUserInfo.USER_MOB+" TEXT);";
    public UserDb(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.e("DATABASE OPERATIONS","DATABASE CREATED / OPENED..");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS","TABLE CREATED..");
    }
    public  void addInformations (String name,String mob, SQLiteDatabase db)
    {
        ContentValues contentValues =  new ContentValues();
        contentValues.put(UserContract.NewUserInfo.USER_NAME,name);
        contentValues.put(UserContract.NewUserInfo.USER_MOB,mob);
        db.insert(UserContract.NewUserInfo.TABLE_NAME,null,contentValues);
        Log.e("DATABASE OPERATIONS","One row Created..");
    }
    public Cursor getInformations (SQLiteDatabase db)
    {Cursor cursor;
        String[] projections = {UserContract.NewUserInfo.USER_NAME, UserContract.NewUserInfo.USER_MOB};
        cursor = db.query(UserContract.NewUserInfo.TABLE_NAME,projections,null,null,null,null,null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
