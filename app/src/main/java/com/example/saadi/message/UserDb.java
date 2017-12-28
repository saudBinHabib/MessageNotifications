package com.example.saadi.message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saadi on 22-Dec-17.
 */

public class UserDb extends SQLiteOpenHelper {

    Context context;

    public UserDb(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
        Log.e("DATABASE OPERATIONS","DATABASE CREATED / OPENED..");
    }

    // Database Version
    private static final int DATABASE_VERSION = 1;


    // Contacts table name
    private static final String TABLE_CATEGORY = "category";
    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_CATEGORY = "category";

    // Database Name
    private static final String DATABASE_NAME = "USERINFO";


    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableCategory(db);
        Log.e("DATATBASE QUERY" , CREATE_QUERY);
        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS","TABLE CREATED..");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY );

        // Create tables again
        onCreate(db);
    }



    public  static final String CREATE_QUERY = "CREATE TABLE "+ TABLE_CONTACTS +
            " ( " +
                KEY_NAME + " " + " TEXT, "+
                KEY_NUMBER + " TEXT, "+
                KEY_CATEGORY + " varchar(50)" +
            ");";



    public void createTableCategory(SQLiteDatabase db){

        String CREATE_TABLE =
                "CREATE TABLE "+ TABLE_CATEGORY +" (" +
                        KEY_CATEGORY + "  varchar(50) NOT NULL" +
                        ");";

        Log.e("DATATBASE QUERY" , CREATE_TABLE);

        db.execSQL(CREATE_TABLE);

    }


    // Adding new Category Information
    long addCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY, category);

        // Inserting Row
        long rowInserted = db.insert(TABLE_CATEGORY, null, values);
        if(rowInserted != -1)
            Toast.makeText(context, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        Log.e("Inserted" , "Data Inserted");
        db.close();
        return rowInserted;// Closing database connection
    }

    // Adding new Contact Information
    void addInformations(DataProvider dp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, dp.getName());
        values.put(KEY_NUMBER, dp.getMob());
        values.put(KEY_CATEGORY,dp.getCategroy());

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }



    /*
    public  void addInformations (String name,String mob, SQLiteDatabase db)
    {
        ContentValues contentValues =  new ContentValues();
        contentValues.put(KEY_NAME,name);
        contentValues.put(KEY,mob);
        db.insert(UserContract.NewUserInfo.TABLE_NAME,null,contentValues);
        Log.e("DATABASE OPERATIONS","One row Created..");
    }
    */
    public Cursor getInformations (SQLiteDatabase db)
    {Cursor cursor;
        String[] projections = {KEY_NAME, KEY_NUMBER};
        cursor = db.query(TABLE_CONTACTS,projections,null,null,null,null,null);
        return cursor;
    }


    // Getting All Contacts
    public List<DataProvider> getInformation() {

        String selectQuery = "SELECT * from "+ TABLE_CONTACTS+" ; " ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        List<DataProvider> contacts = new ArrayList<DataProvider>();
        if (cursor.moveToFirst()) {
            do {
                DataProvider dp = new DataProvider();
                int column1 = cursor.getColumnIndex(KEY_NAME);
                dp.setName(cursor.getString(column1));
                int column2 = cursor.getColumnIndex(KEY_NUMBER);
                dp.setMob(cursor.getString(column2));
                int column3 = cursor.getColumnIndex(KEY_CATEGORY);
                dp.setCategroy(cursor.getString(column3));
                contacts.add(dp);
                // Adding contact to list
            } while (cursor.moveToNext());
        }

        // return contact list
        return contacts;
    }


    // Getting All Reminders
    public String[] getCategories() {

        String selectQuery = "SELECT DISTINCT "+ KEY_CATEGORY+" FROM  " + TABLE_CATEGORY ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String [] room = new String[cursor.getCount()];
        // looping through all rows and adding to list
        int counter = 0;
        if (cursor.moveToFirst()) {
            do {
                int column = cursor.getColumnIndex(KEY_CATEGORY);
                room[counter] = cursor.getString(column);
                counter++;
                // Adding contact to list
            } while (cursor.moveToNext());
        }

        // return contact list
        return room;
    }

}
