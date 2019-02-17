package com.example.katalogfilm.feature.detailmovieactivity;

import com.example.katalogfilm.data.api.ApiRetrofit;
import com.example.katalogfilm.data.entity.MovieItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter {
    DetailView view;

    public DetailPresenter(DetailView view){
        this.view=view;
    }

    public void getData(String id){
        view.showLoad();

        Call<MovieItem> movieItemCall = ApiRetrofit.getService().getDetailMovie(id);

        movieItemCall.enqueue(new Callback<MovieItem>() {
            @Override
            public void onResponse(Call<MovieItem> call, Response<MovieItem> response) {
                view.finishLoad();
                view.showData(response.body());
            }

            @Override
            public void onFailure(Call<MovieItem> call, Throwable t) {

            }
        });
    }

}
