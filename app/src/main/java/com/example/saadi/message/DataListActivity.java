package com.example.saadi.message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        List<DataProvider> data = new ArrayList<>();
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DataListActivity.this, Notification.class);
                intent.putExtra("type", "single");
                intent.putExtra("number" , data.get(position).getMob() );
                startActivity(intent);
            }
        });

        userDb = new UserDb(getApplicationContext());
        sqLiteDatabase = userDb.getReadableDatabase();
        cursor = userDb.getInformations(sqLiteDatabase);
        if (cursor.moveToFirst()){
            do {
                String name,mob,cat;
                name=  cursor.getString(0);
                mob= cursor.getString(1);
                cat = cursor.getString(2);
                DataProvider dataProvider = new DataProvider();
                dataProvider.setName(name);
                dataProvider.setMob(mob);
                dataProvider.setCategroy(cat);
                data.add(dataProvider);
                listDataAdapter.add(dataProvider);
            }while (cursor.moveToNext());

        }
    }
}
