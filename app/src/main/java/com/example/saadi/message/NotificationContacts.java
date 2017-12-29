package com.example.saadi.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class NotificationContacts extends AppCompatActivity {
    UserDb db;
    ListView lstContacts;
    List<DataProvider> contacts;
    String category=null;
    Intent intent;
    EditText et;
    NotificationList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new UserDb(this);
        et = (EditText) findViewById(R.id.etSearchName);
        if(getIntent().getStringExtra("category")!= null){
            category = getIntent().getStringExtra("category");
            contacts = db.getInformation(category);
        }
        lstContacts = (ListView) findViewById(R.id.notificationContact);
        adapter = new NotificationList(this, contacts);
        lstContacts.setAdapter(adapter);
        lstContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String number = contacts.get(position).getMob();
                intent = new Intent(NotificationContacts.this, Notification.class);
                intent.putExtra("type", "single");
                intent.putExtra("number" , number );
                startActivity(intent);
            }
        });

    }

    public void sendCategory(View view) {

        intent = new Intent(NotificationContacts.this, Notification.class);
        intent.putExtra("type", "category");
        intent.putExtra("category" ,category );
        startActivity(intent);

    }


    public void searchContact(View view) {
        String search = et.getText().toString();
        if (search.isEmpty()) {
            Toast.makeText(this, "Please Enter Any Character of the Name", Toast.LENGTH_SHORT).show();
        } else {
            contacts = db.getSpecificInformation(category,search);
            adapter.overMethod(contacts);
            Toast.makeText(this, "Data set Changeds", Toast.LENGTH_SHORT).show();
        }
    }
}
