package com.example.deathblade.saveme_server.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class RescueProvider extends ContentProvider {


    public static final int RESCUE = 100;
    public static final int RESCUE_ID = 101;

    /**
     * Creating the Uri Matcher.
     */
    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    /**
     * Adding the Uris to the matcher.
     */
    static {

        /**
         * Uri for all the details.
         */
        sUriMatcher.addURI(RescueContract.CONTENT_AUTHORITY, RescueContract.PATH_RESCUE, RESCUE);

        /**
         * Uri for a single res.
         */
        sUriMatcher.addURI(RescueContract.CONTENT_AUTHORITY, RescueContract.PATH_RESCUE + "/#", RESCUE_ID);
    }


    private RescueDBHelper mDbHelper;


    @Override
    public boolean onCreate() {
        mDbHelper = new RescueDBHelper(getContext());
        return true;


    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match) {

            case RESCUE:
                cursor = database.query(RescueContract.RescueEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case RESCUE_ID:
                selection = RescueContract.RescueEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(RescueContract.RescueEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Unknown Uri.");
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    private Uri insertRescueDetail(Uri uri, ContentValues contentValues) {

        /**
         *  TODO: Sanity Checks.
         */

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(RescueContract.RescueEntry.TABLE_NAME, null, contentValues);

        if (id == -1) {
            Log.e("RescueProvider", "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case RESCUE:
                Log.i("qwertyuiocvbnm", "1234567891234567890-234567890");
                return insertRescueDetail(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private int updateTrip(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        /**
         * TODO: Sanity Checks.
         */

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        int numberRowsChanged = database.update(RescueContract.RescueEntry.TABLE_NAME, values, selection, selectionArgs);

        if (numberRowsChanged > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberRowsChanged;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        switch (match) {
            case RESCUE:
                return updateTrip(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case RESCUE:
                rowsDeleted = database.delete(RescueContract.RescueEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case RESCUE_ID:
                selection = RescueContract.RescueEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(RescueContract.RescueEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case RESCUE:
                return RescueContract.RescueEntry.CONTENT_LIST_TYPE;
            case RESCUE_ID:
                return RescueContract.RescueEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }


}
