package com.example.katalogfilm.feature.upcomingfrag;

import com.example.katalogfilm.data.entity.Movie;

import java.util.ArrayList;

public interface UpcomingView {
    void showLoad();

    void finishLoad();

    void showList(ArrayList<Movie> data);

    void noData();
}
