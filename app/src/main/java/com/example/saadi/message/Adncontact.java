package com.example.saadi.message;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Adncontact extends AppCompatActivity {
    EditText editTextPhone;
    EditText Name;
    Button btn;
    Context context = this;
    UserDb userDb;
    SQLiteDatabase sqLiteDatabase;

    String name , number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adncontact);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        Name = (EditText) findViewById(R.id.editTextName);
        if(getIntent().getStringExtra("type").equalsIgnoreCase("single")){
            name = getIntent().getStringExtra("name");
            number = getIntent().getStringExtra("number");
            editTextPhone.setText(number);
            Name.setText(name);
        }else{
            editTextPhone.setText("Majboor");
            Name.setText("Rabia");
        }
        btn = (Button) findViewById(R.id.savebtn);

    }
    public void addContact(View view){
        String name =  Name.getText().toString();
        String mob = editTextPhone.getText().toString();
        userDb = new UserDb(context);
        sqLiteDatabase = userDb.getWritableDatabase();
        userDb.addInformations(name,mob,sqLiteDatabase);
        Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show();
    }
}
