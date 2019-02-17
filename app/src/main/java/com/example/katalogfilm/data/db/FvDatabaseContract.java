package com.example.katalogfilm.data.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class FvDatabaseContract {

    public static final String TABLE_FV = "TABLE_FV";
    public static final String AUTHORITY = "com.example.katalogfilm";
    private static final String SCHEME = "content";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_FV)
            .build();

    public static final class favoriteColumns implements BaseColumns{
        public static String MOVIE_ID = "movie_id";
        public static String TITLE = "title";
        public static String POSTER_PATH = "poster_path";
        public static String OVERVIEW = "overview";

    }
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static float getColumnFloat(Cursor cursor, String columnName) {
        return cursor.getFloat(cursor.getColumnIndex(columnName));
    }
}
