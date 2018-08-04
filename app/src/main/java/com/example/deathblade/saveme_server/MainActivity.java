package com.example.deathblade.saveme_server;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

    if (cursor.moveToFirst()) { // must check the result to prevent exception
        do {
            String msgData = "";
            for(int idx=0;idx<cursor.getColumnCount();idx++)
            {
                msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
            }
            // use msgData
            Toast.makeText(this,msgData, Toast.LENGTH_SHORT);
        } while (cursor.moveToNext());
    }
    else {
        // empty box, no SMS
    }
}}
