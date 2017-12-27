package com.example.saadi.message;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Contact extends AppCompatActivity {
    Button btn1, btn2,btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        btn1 = (Button) findViewById(R.id.new_con);
        btn2 = (Button) findViewById(R.id.cfl);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Contact.this, ImportFromCl.class);
                startActivity(intent);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contact.this, Adncontact.class);
                startActivity(intent);
            }
        });
        btn3 = (Button) findViewById(R.id.vl);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contact.this, DataListActivity.class);
                startActivity(intent);
            }
        });
    }
}
