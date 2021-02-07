package com.warpdev.giphytest;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * TrendPage에 대한 ViewModel
 * PagedList를 만든다.
 *
 * @author warpdev
 */
public class TrendViewModel extends AndroidViewModel {
    private LiveData<PagedList<ImageData>> pagedListLiveData;


    public TrendViewModel(@NonNull Application application) {
        super(application);
        TrendDataFactory trendDataFactory = new TrendDataFactory();

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
