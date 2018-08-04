package com.example.deathblade.saveme_server;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public static final String OTP_REGEX = "^$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {

                //From the received text string you may do string operations to get the required OTP
                //It depends on your SMS format
                Log.e("Message",messageText);
                Toast.makeText(MainActivity.this,"Message: "+messageText,Toast.LENGTH_LONG).show();

                // If your OTP is six digits number, you may use the below code

                Pattern pattern = Pattern.compile(OTP_REGEX);
                Matcher matcher = pattern.matcher(messageText);
                String otp = "";
                while (matcher.find())
                {
                    otp = matcher.group();
                }

                Toast.makeText(MainActivity.this,"OTP: "+ otp ,Toast.LENGTH_LONG).show();
                if (otp==""){
                    Toast.makeText(MainActivity.this,"OTP not working" , Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}