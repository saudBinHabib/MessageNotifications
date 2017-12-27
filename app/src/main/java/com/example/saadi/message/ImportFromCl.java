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

        // Get the Cursor of all the contacts

        String phoneNumber = null;
        Uri ContacUri = ContactsContract.Contacts.CONTENT_URI;
        String displayName = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        String number = ContactsContract.CommonDataKinds.Phone.NUMBER;
        String _ID = ContactsContract.Contacts._ID;
        Uri PhoneContractURI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;

        Cursor cursor = getContentResolver().query(ContacUri,null,null,null,null );
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(displayName));
                if(value1 != null) {
                    if (value1.contains(name)) {
                        continue;
                    }
                }
                value1.add(name);
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                if(hasPhoneNumber>0) {
                    Cursor phoneCursor = getContentResolver().query(PhoneContractURI, null, PHONE_CONTACT_ID + "=?", new String[]{contact_id}, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(number));
                        value2.add(phoneNumber);
                    }
                    phoneCursor.close();
                }
            }
            cursor.close();
            namearray = value1.toArray(new String[0]);
            phonearray = value2.toArray(new String[0]);
            for(int i=0; i< namearray.length; i++){
                contacts.add(new DataProvider(namearray[i],phonearray[i]));
            }
        }


        return contacts;
    }
}
