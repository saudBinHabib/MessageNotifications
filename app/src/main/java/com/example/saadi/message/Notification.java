package com.example.saadi.message;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Notification extends AppCompatActivity {

    UserDb db;
    EditText etNotifications;
    String message = null;
    TextView tvTitle;

    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_SEND_SMS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        db = new UserDb(this);
        etNotifications = (EditText) findViewById(R.id.edMessage);
        tvTitle = (TextView) findViewById(R.id.notificationTitle);
    }

    public void sendMessage(View view) {
        message = etNotifications.getText().toString();
        sendMessages();
    }



    private void sendMessages() {
        // Check the SDK version and whether the permission is already granted or not.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_SEND_SMS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted
            if(message!=null) {
                if (getIntent().getExtras().getString("type").equalsIgnoreCase("single")) {
                    String phoneNo = getIntent().getExtras().getString("number");
                    tvTitle.setText("Send to " + phoneNo);
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo, null, message, null, null);
                        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();

                    }
                } else if (getIntent().getExtras().getString("type").equalsIgnoreCase("category")) {
                    String category = getIntent().getExtras().getString("category");
                    tvTitle.setText("Send to " + category);
                    String[] numberList = db.getNumbersAccordingToCategory(category);
                    //   Toast.makeText(FacultyNotifications.this, "size" +facultyList.size(), Toast.LENGTH_SHORT).show();
                    for (String number : numberList) {
                        try {
                            //       etNotifications.setText(etNotifications.getText().toString() + f.getPhone());
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(number, null, message, null, null);
                        } catch (Exception e) {
                            Toast.makeText(this, "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        }
                    }
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                } else if (getIntent().getExtras().getString("type").equalsIgnoreCase("complete")) {
                    tvTitle.setText("Send to All Contacts");
                    String[] numberList = db.getAllNumbers();
                    //   Toast.makeText(FacultyNotifications.this, "size" +facultyList.size(), Toast.LENGTH_SHORT).show();
                    for (String number : numberList) {
                        try {
                            //       etNotifications.setText(etNotifications.getText().toString() + f.getPhone());
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(number, null, message, null, null);
                        } catch (Exception e) {
                            Toast.makeText(this, "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        }
                    }
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Please Enter the Message First", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                sendMessages();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
