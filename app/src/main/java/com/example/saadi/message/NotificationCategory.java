package com.example.saadi.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NotificationCategory extends AppCompatActivity {

    ListView lstCategory;
    UserDb db;
    String category[];
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new UserDb(this);
        category = db.getCategories();
        lstCategory = (ListView) findViewById(R.id.notificationCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,category);
        lstCategory.setAdapter(adapter);
        lstCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cat = category[position];
                intent = new Intent(NotificationCategory.this, NotificationContacts.class);
                intent.putExtra("category" , cat);
                startActivity(intent);
            }
        });
    }

    public void sendAll(View view) {

        intent = new Intent(NotificationCategory.this, Notification.class);
        intent.putExtra("type", "complete");
        startActivity(intent);


    }

}
