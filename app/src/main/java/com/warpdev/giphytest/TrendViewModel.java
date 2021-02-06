package com.warpdev.giphytest;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class TrendViewModel extends AndroidViewModel {
    private LiveData<PagedList<ImageData>> pagedListLiveData;


    public TrendViewModel(@NonNull Application application) {
        super(application);
        TrendDataFactory trendDataFactory = new TrendDataFactory();
        MutableLiveData<TrendPageKeyedDataSource> dataSourceMutableLiveData = trendDataFactory.getImageDataLiveData();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(25)
                .setPageSize(25)
                .setMaxSize(100)
                .build();
        pagedListLiveData = new LivePagedListBuilder<Integer, ImageData>(trendDataFactory, config).build();
    }

    public LiveData<PagedList<ImageData>> getPagedListLiveData() {
        return pagedListLiveData;
    }
}
