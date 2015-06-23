package com.suwonsmartapp.hello.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.suwonsmartapp.hello.activity.db.DbHelper;

public class MyContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.suwonsmartapp.hello.provider";
    private static final String TABLE = "Address";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + TABLE);

    // Creates a UriMatcher object.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int ALL = 1;
    private static final int SINGLE = 2;

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE, ALL);
        sUriMatcher.addURI(AUTHORITY, TABLE + "/#", SINGLE);
    }

    private DbHelper mDbHelper;

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sUriMatcher.match(uri);
        long id = -1;
        switch (uriType) {
            case ALL:
                id = mDbHelper.getWritableDatabase().insert(TABLE, null, values);
                break;
            default:
                throw new IllegalAccessError("Unknown URI + " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(TABLE + "/" + id);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
