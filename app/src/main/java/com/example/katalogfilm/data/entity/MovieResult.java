package com.example.katalogfilm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResult {
    @SerializedName("results")
    private ArrayList<Movie> result;

    public ArrayList<Movie> getResult() {
        return result;
    }

}
