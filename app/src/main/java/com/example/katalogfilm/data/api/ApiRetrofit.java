package com.example.katalogfilm.data.api;

import com.example.katalogfilm.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {
    //private static final String BASE_URL = "https://api.themoviedb.org/";

    public static RetrofitInterface getService(){
        return getRetrofit().create(RetrofitInterface.class);
    }

    public static Retrofit.Builder builder() {
        return new Retrofit.Builder().baseUrl(BuildConfig.TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
    }

    public static Retrofit getRetrofit() {
        return builder().build();
    }
}
