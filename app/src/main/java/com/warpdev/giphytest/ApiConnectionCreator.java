package com.warpdev.giphytest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConnectionCreator {
    private Retrofit retrofit;

    private TrendingInterface trendingInterface;

    public ApiConnectionCreator() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.giphy.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        trendingInterface = retrofit.create(TrendingInterface.class);
    }
    public TrendingInterface getTrendingInterface() {
        return trendingInterface;
    }
}
