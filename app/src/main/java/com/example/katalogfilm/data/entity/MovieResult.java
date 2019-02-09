package com.example.katalogfilm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResult {
    @SerializedName("results")
    private List<Movie> result;

    public List<Movie> getResult() {
        return result;
    }

    public void setResult(List<Movie> result) {
        this.result = result;
    }
}
