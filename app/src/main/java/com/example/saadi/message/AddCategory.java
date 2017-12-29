package com.example.saadi.message;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCategory extends AppCompatActivity {

    EditText et;
    UserDb db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        et = (EditText)findViewById(R.id.etCategory);
        db = new UserDb(this);

    }

    public void addCategory(View view) {
        String cat = et.getText().toString();
        if(cat.isEmpty()){
            Toast.makeText(this, "Please Fill the Data.", Toast.LENGTH_SHORT).show();
        }else{
            long rowInserted = db.addCategory(cat);

            if(rowInserted != -1)
                Toast.makeText(this, "New Category Inserted " , Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
        }

    }
}
