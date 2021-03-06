package com.warpdev.giphytest;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

/**
 * PagedList를 만들기 위한 DataSource.Factory
 *
 * @author warpdev
 */
public class TrendDataFactory extends DataSource.Factory {

    private MutableLiveData<TrendPageKeyedDataSource> imageDataLiveData = new MutableLiveData<>();

    private TrendPageKeyedDataSource lastSource;

    @NonNull
    @Override
    public DataSource<Integer, ImageData> create() {
        lastSource= new TrendPageKeyedDataSource();
        imageDataLiveData.postValue(lastSource);
        return lastSource;
    }
}
