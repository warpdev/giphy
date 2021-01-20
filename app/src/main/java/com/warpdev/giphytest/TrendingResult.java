package com.warpdev.giphytest;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Json형식으로 받은 값을 처리하기위한 클래스
 * Serialized가 필요없는 것도 있지만 일관성과 가독성을 위해 형식을 맞춤.
 * @see ImageData
 *
 * @author warpdev
 */
public class TrendingResult {
    @SerializedName("data")
    private ArrayList<ImageData> data;

    public ArrayList<ImageData> getData() {
        return data;
    }

}
