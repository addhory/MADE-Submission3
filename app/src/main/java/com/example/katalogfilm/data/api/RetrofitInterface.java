package com.example.katalogfilm.data.api;

import com.example.katalogfilm.BuildConfig;
import com.example.katalogfilm.data.entity.MovieItem;
import com.example.katalogfilm.data.entity.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {
    //String API = "fb30ef4173f7e7c8051360b4d8a58e29";
    @GET("3/movie/now_playing?api_key="+ BuildConfig.TMDB_API_KEY +"&page=1")
    Call<MovieResult> getNowPlaying();

    @GET("3/search/movie?api_key="+BuildConfig.TMDB_API_KEY+"&language=en-US")
    Call<MovieResult> getSearchMovie(@Query("query") String string);

    @GET("3/movie/{id}?api_key="+BuildConfig.TMDB_API_KEY+"&language=en-US")
    Call<MovieItem> getDetailMovie(@Path("id") String id);

    @GET("3/movie/upcoming?api_key="+ BuildConfig.TMDB_API_KEY +"&page=1")
    Call<MovieResult> getUpcoming();
}
