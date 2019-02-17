package com.example.katalogfilm.data.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.katalogfilm.data.db.DatabaseHelper;
import com.example.katalogfilm.data.db.FvDatabaseContract;
import com.example.katalogfilm.data.db.FvMovieHelper;

import java.util.Objects;

import static com.example.katalogfilm.data.db.FvDatabaseContract.AUTHORITY;
import static com.example.katalogfilm.data.db.FvDatabaseContract.CONTENT_URI;

public class MovieProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private FvMovieHelper mvHelper;

    static{
        sUriMatcher.addURI(AUTHORITY, FvDatabaseContract.TABLE_FV, MOVIE);
        sUriMatcher.addURI(AUTHORITY, FvDatabaseContract.TABLE_FV + "/#", MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        mvHelper = new FvMovieHelper(getContext());
        mvHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = mvHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = mvHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = mvHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = mvHelper.deleteProvider(uri.getLastPathSegment());
                break;

            default:
                deleted = 0;
        }

        if (deleted > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return deleted;    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
