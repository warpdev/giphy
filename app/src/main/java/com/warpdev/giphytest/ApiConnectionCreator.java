package com.warpdev.giphytest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * GIPHY API 에 대 Retrofit 접속을 위 Interface 객체를 만들어주는 클래스
 *
 * @author warpdev
 */
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
