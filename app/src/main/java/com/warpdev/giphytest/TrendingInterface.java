package com.warpdev.giphytest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit을 이용하여 Giphy의 Trending Gif를 얻어오기 위한 Interface
 */

public interface TrendingInterface {

    /**
     * Trending Gif 목록을 지정된 offset부터 limit만큼 가져옴
     *
     * @param key      GIPHY API Key.
     * @param offset   Specifies the starting position of the results.
     * @param limit    The maximum number of objects to return.
     *
     * @return         TrendingResult의 형태로 가져온다. (TrendingResult.java 참고)
     */
    @GET("gifs/trending")
    Call<TrendingResult> getList(@Query("api_key") String key, @Query("offset") int offset, @Query("limit") int limit);
}
