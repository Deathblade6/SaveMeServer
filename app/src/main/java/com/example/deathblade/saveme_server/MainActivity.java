package com.example.deathblade.saveme_server;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deathblade.saveme_server.data.RescueContract;
import com.example.deathblade.saveme_server.data.RescueProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private double latitude, longitude;
    private String time, sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(getData());
            }
        });

        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(getDummyData());
            }
        });
    }


    private void sendSMS(String data) {
        SmsManager smsManager = SmsManager.getDefault();
        String msg = data;
        smsManager.sendTextMessage("7025266580", null, msg, null, null);
        //      smsManager.sendTextMessage("8527625569", null, msg, null, null);
        Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
    }

    private String getData() {
        String s;
        Cursor cursor = getContentResolver().query(RescueContract.RescueEntry.CONTENT_URI, null, null, null, null);
        int numberColumnIndex = cursor.getColumnIndex(RescueContract.RescueEntry.COLUMN_PH_NUMBER);
        int latColumnIndex = cursor.getColumnIndex(RescueContract.RescueEntry.COLUMN_LATITUDE);
        int longColumnIndex = cursor.getColumnIndex(RescueContract.RescueEntry.COLUMN_LONGITUDE);
        int timeColumnIndex = cursor.getColumnIndex(RescueContract.RescueEntry.COLUMN_TIME);

        StringBuilder builder = new StringBuilder();

        while (cursor.moveToNext()) {


            builder.append(cursor.getLong(numberColumnIndex));
            builder.append(",");
            builder.append(cursor.getDouble(latColumnIndex));
            builder.append(",");
            builder.append(cursor.getDouble(longColumnIndex));
            builder.append(",");
            builder.append(cursor.getString(timeColumnIndex));
            builder.append("\n");

        }

        cursor.close();

        s = builder.toString();

        Log.i("MainActivity", "getData: " + s);

        return s;
    }


    private String getDummyData() {
        String s = "9a8s7wjd6ah26ad7v6z65s6d6ac6x5v2qqh21qwbenqbewn";

        return s;
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
//        Cursor cursor = getContentResolver().query(RescueContract.RescueEntry.CONTENT_URI, null, null, null, null);
//
//        int numberColumnIndex = cursor.getColumnIndex(RescueContract.RescueEntry.COLUMN_PH_NUMBER);
//        int idColumnIndex = cursor.getColumnIndex(RescueContract.RescueEntry._ID);
//        long id = -1;
//        while (cursor.moveToNext()) {
//            if (cursor.getLong(numberColumnIndex) == number) {
//                id = cursor.getLong(idColumnIndex);
//                break;
//            }
//        }
//
//        if (id != -1) {
//            String s = RescueContract.RescueEntry._ID + "?=";
//            String[] strings = new String[]{"" + id};
//            getContentResolver().update(RescueContract.RescueEntry.CONTENT_URI, values, s, strings);
//        } else {
//

        getContentResolver().insert(RescueContract.RescueEntry.CONTENT_URI, values);
//        }
        Cursor cursor = getContentResolver().query(RescueContract.RescueEntry.CONTENT_URI, null, null, null, null);

        int c = cursor.getCount();
        String s = "" + c;
        TextView textView = (TextView) findViewById(R.id.count_view);
        textView.setText(s);

        cursor.close();
    }

}