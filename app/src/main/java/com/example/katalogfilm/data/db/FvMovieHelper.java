package com.example.katalogfilm.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.katalogfilm.data.entity.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.katalogfilm.data.db.FvDatabaseContract.TABLE_FV;
import static com.example.katalogfilm.data.db.FvDatabaseContract.favoriteColumns.MOVIE_ID;
import static com.example.katalogfilm.data.db.FvDatabaseContract.favoriteColumns.OVERVIEW;
import static com.example.katalogfilm.data.db.FvDatabaseContract.favoriteColumns.POSTER_PATH;
import static com.example.katalogfilm.data.db.FvDatabaseContract.favoriteColumns.TITLE;

public class FvMovieHelper {
    private static String DATABASE_TABLE = TABLE_FV;
    private Context context;
    private DatabaseHelper helper;
    private SQLiteDatabase database;

    public FvMovieHelper(Context context) {
        this.context = context;
    }
    public FvMovieHelper open() throws SQLException {
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
        return this;
    }
    public ArrayList<Movie> query() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_ID)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));


                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Movie movie){
        ContentValues values= new ContentValues();
        values.put(MOVIE_ID, movie.getId());
        values.put(POSTER_PATH, movie.getPosterPath());
        values.put(OVERVIEW, movie.getOverview());
        values.put(TITLE, movie.getTitle());
        return database.insert(DATABASE_TABLE, null, values);
    }
    public int update(Movie movie){
        ContentValues values= new ContentValues();
        values.put(MOVIE_ID, movie.getId());
        values.put(POSTER_PATH, movie.getPosterPath());
        values.put(OVERVIEW, movie.getOverview());
        values.put(TITLE, movie.getTitle());
        return database.update(DATABASE_TABLE, values, MOVIE_ID + "= '" + movie.getId() + "'", null);
    }
    public int delete(String movieId){
        return database.delete(DATABASE_TABLE, MOVIE_ID + " = '" + movieId + "'", null);

    }
    public Cursor queryByIdProvider(String movieId) {
        return database.query(DATABASE_TABLE,
                null,
                MOVIE_ID + " = ?",
                new String[]{movieId},
                null,
                null,
                null,
                null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE

                , null
                , null
                , null
                , null
                , null
                , TITLE + " DESC");
    }
    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, MOVIE_ID + " = ?", new String[]{id});
    }

}
