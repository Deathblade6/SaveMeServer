package com.example.deathblade.saveme_server.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RescueDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rescue.db";

    private static final int DATABASE_VERSION = 1;

    public RescueDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /**
         * Creating the rescue table.
         */
        String SQL_CREATE_TABLE = "CREATE TABLE " + RescueContract.RescueEntry.TABLE_NAME + " ("
                + RescueContract.RescueEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RescueContract.RescueEntry.COLUMN_COUNTRY_CODE + " TEXT, "
                + RescueContract.RescueEntry.COLUMN_PH_NUMBER + " INTEGER, "
                + RescueContract.RescueEntry.COLUMN_LATITUDE + " DECIMAL(12,10), "
                + RescueContract.RescueEntry.COLUMN_LONGITUDE + " DECIMAL(12,10), "
                + RescueContract.RescueEntry.COLUMN_TIME + " TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
