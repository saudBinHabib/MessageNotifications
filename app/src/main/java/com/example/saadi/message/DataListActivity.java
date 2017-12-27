package com.example.saadi.message;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DataListActivity extends AppCompatActivity {
        ListView listView;
        SQLiteDatabase sqLiteDatabase;
        UserDb userDb;
        Cursor cursor;
        ListDataAdapter listDataAdapter;
        SharedPreferences sp;
        SharedPreferences.Editor editor;
        List<Contact> lstContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_list_layout);

        sp = DataListActivity.this.getSharedPreferences("ADA_PREF",MODE_PRIVATE);
        editor = sp.edit();
        lstContact = new ArrayList<>();

        listView = (ListView) findViewById(R.id.sam);
        listDataAdapter = new ListDataAdapter(getApplicationContext(),R.layout.row_layout);
        listView.setAdapter(listDataAdapter);

        userDb = new UserDb(getApplicationContext());
        sqLiteDatabase = userDb.getReadableDatabase();
        cursor = userDb.getInformations(sqLiteDatabase);
        if (cursor.moveToFirst()){
            do {
                String name,mob;
                name=  cursor.getString(0);
                mob= cursor.getString(1);
                DataProvider dataProvider = new DataProvider(name,mob);
                listDataAdapter.add(dataProvider);
            }while (cursor.moveToNext());

        }
    }
}
