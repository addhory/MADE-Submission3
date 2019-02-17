package com.example.katalogfilm.data.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.example.katalogfilm.data.db.FvDatabaseContract.favoriteColumns.MOVIE_ID;
import static com.example.katalogfilm.data.db.FvDatabaseContract.favoriteColumns.OVERVIEW;
import static com.example.katalogfilm.data.db.FvDatabaseContract.favoriteColumns.POSTER_PATH;
import static com.example.katalogfilm.data.db.FvDatabaseContract.favoriteColumns.TITLE;
import static com.example.katalogfilm.data.db.FvDatabaseContract.getColumnString;

public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("id")
    private String id;


    public Movie() {
    }
    public Movie(Cursor cursor) {
        this.title= getColumnString(cursor, TITLE);
        this.id=getColumnString(cursor, MOVIE_ID);
        this.overview=getColumnString(cursor, OVERVIEW);
        this.posterPath=getColumnString(cursor, POSTER_PATH);
    }
    public Movie(String title, String id, String overview, String posterPath){
        this.id = id;
        this.title=title;
        this.overview=overview;
        this.posterPath=posterPath;
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.id = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeString(this.id);
    }
}