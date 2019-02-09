package com.example.katalogfilm.feature.detailmovieactivity;

import com.example.katalogfilm.data.entity.MovieItem;

public interface DetailView {

    void showLoad();

    void finishLoad();

    void showData(MovieItem movieItem);
}

