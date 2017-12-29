package com.example.saadi.message;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class Adncontact extends AppCompatActivity {
    EditText editTextPhone;
    EditText Name;
    Button btn;
    UserDb db;
    SQLiteDatabase sqLiteDatabase;
    String category=null;
    Spinner sCategory;
    String name , number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adncontact);

        sCategory = (Spinner) findViewById(R.id.spinnerSf);
        db = new UserDb(this);
        final String[] categoryArray = db.getCategories();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categoryArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCategory.setAdapter(adapter);

        sCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = categoryArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        Name = (EditText) findViewById(R.id.editTextName);
        if(getIntent().getStringExtra("type")!=null) {
            if (getIntent().getStringExtra("type").equalsIgnoreCase("single")) {
                name = getIntent().getStringExtra("name");
                number = getIntent().getStringExtra("number");
                editTextPhone.setText(number);
                Name.setText(name);
            }
        }
        btn = (Button) findViewById(R.id.savebtn);

    }
    public void addContact(View view){
        String name =  Name.getText().toString();
        String mob = editTextPhone.getText().toString();
        if(name.isEmpty() || mob.isEmpty() || category == null ){
            Toast.makeText(this, "Please Fill all the data.", Toast.LENGTH_SHORT).show();
        }else{
            DataProvider d= new DataProvider();
            d.setMob(mob);
            d.setName(name);
            d.setCategroy(category);
            int check = db.addInformations(d);

            if(check==1){
                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Contact already exists in this category", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
