package com.example.katalogfilm.data.db;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.katalogfilm.data.entity.Movie;

import java.util.ArrayList;

import static com.example.katalogfilm.data.db.FvDatabaseContract.CONTENT_URI;

public class LoaderDb  extends AsyncTaskLoader<ArrayList<Movie>> {


    public LoaderDb(@NonNull Context context) {
        super(context);
        onContentChanged();
    }
    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        if(takeContentChanged())
            forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Movie> loadInBackground() {
        ArrayList<Movie> list = new ArrayList<>();

        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI, null,null,null,null,null);
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                Movie movie= new Movie(cursor);
                list.add(movie);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        if (cursor!=null){
            cursor.close();
        }
        return list;
    }
    @Override
    public void deliverResult(ArrayList<Movie> data) {
        super.deliverResult(data);

    }
    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();

    }
}
