package com.example.saadi.message;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.Os;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ImportFromCl extends AppCompatActivity {
    // The ListView
    private ListView lstContact;
    List<DataProvider> lstDataProvider ;
    String namearray[];
    String phonearray[];


    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_from_cl);
        // Find the list view
        lstContact = (ListView) findViewById(R.id.listView1);

        // Read and show the contacts
        showContacts();
        lstContact.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ImportFromCl.this, Adncontact.class);
                intent.putExtra("type","single");
                intent.putExtra("number",lstDataProvider.get(position).getMob());
                intent.putExtra("name",lstDataProvider.get(position).getName());
                startActivity(intent);
            }
        });
    }

    /**
     * Show the contacts in the ListView.
     */
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.

           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
               requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
               //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
           } else {
               // Android version is lesser than 6.0 or the permission is already granted
               lstDataProvider = getContactNamesAndNumber();
             //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
               ImportListAdapter adapter = new ImportListAdapter(this,lstDataProvider);
               lstContact.setAdapter(adapter);
           }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Read the name of all the contacts.
     *
     * @return a list of names.
     */
  /*
    private List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        // Close the curosor
        cursor.close();

        return contacts;
    }
*/
    private List<DataProvider> getContactNamesAndNumber() {
        List<DataProvider> contacts = new ArrayList<>();
        List<String> value1 = new ArrayList<>();
        List<String> value2 = new ArrayList<>();

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            //Read Contact Name
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //Read Phone Number
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            if(value1!=null || value2!=null) {
                if(value1.contains(name)){
                    continue;
                }
            }
            value1.add(name);
            value2.add(phoneNumber);
        }
        phones.close();

        namearray = value1.toArray(new String[0]);
        phonearray = value2.toArray(new String[0]);
        for(int i=0; i< namearray.length; i++){
            contacts.add(new DataProvider(namearray[i],phonearray[i]));
        }

        return contacts;
    }
}
