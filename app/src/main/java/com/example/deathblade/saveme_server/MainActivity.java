package com.example.deathblade.saveme_server;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.deathblade.saveme_server.data.RescueContract;
import com.example.deathblade.saveme_server.data.RescueProvider;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private double latitude, longitude;
    private String time, sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText, String senderText) {

                //From the received text string you may do string operations to get the required OTP
                //It depends on your SMS format
                Log.e("Message", messageText);
                String[] messages;
                messages = messageText.split(",");
                latitude = Double.valueOf(messages[0]);
                longitude = Double.valueOf(messages[1]);
                time = messages[2];
                sender = senderText;
                savePerson();
            }
        });
    }

    private void savePerson() {
        String countryCode = sender.substring(0, 3);
        String numberString = sender.substring(3);
        long number = Long.valueOf(numberString);
        Toast.makeText(MainActivity.this, "Message: " + number, Toast.LENGTH_LONG).show();
        ContentValues values = new ContentValues();
        values.put(RescueContract.RescueEntry.COLUMN_COUNTRY_CODE, countryCode);
        values.put(RescueContract.RescueEntry.COLUMN_PH_NUMBER, number);
        values.put(RescueContract.RescueEntry.COLUMN_LATITUDE, latitude);
        values.put(RescueContract.RescueEntry.COLUMN_LONGITUDE, longitude);
        values.put(RescueContract.RescueEntry.COLUMN_TIME, time);

        Cursor cursor = getContentResolver().query(RescueContract.RescueEntry.CONTENT_URI, null, null, null, null);
        if (cursor != null)
            Toast.makeText(this, "" + cursor.getCount(), Toast.LENGTH_SHORT).show();
        getContentResolver().insert(RescueContract.RescueEntry.CONTENT_URI, values);

    }

}