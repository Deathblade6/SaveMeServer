package com.example.deathblade.saveme_server.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class RescueContract {

    private RescueContract(){}

    /**
     * Content authority for the content provider.
     */
    public static final String CONTENT_AUTHORITY = "com.example.deathblade.saveme_server.data";

    /**
     * Uri from the above content authority.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible paths.
     */
    public static final String PATH_RESCUE = "details";

    public static final class RescueEntry implements BaseColumns {


        /**
         * Uri for this whole table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_RESCUE);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of trips.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESCUE;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single trip.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESCUE;

        /**
         * String with the name of the database table.
         */
        public static final String TABLE_NAME = "details";

        // Columns in the table.


        /** Type : INTEGER */
        public static final String _ID = BaseColumns._ID;

        /** Type: TEXT */
        public static final String COLUMN_COUNTRY_CODE = "ccode";

        /** Type : INTEGER */
        public static final String COLUMN_PH_NUMBER = "phno";

        /** Type: DECIMAL(12,10) */
        public static final String COLUMN_LATITUDE = "latitude";

        /** Type: DECIMAL(12,10) */
        public static final String COLUMN_LONGITUDE = "longitude";

        /** Type : TEXT */
        public static final String COLUMN_TIME = "time";


    }
}
