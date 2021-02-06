package com.warpdev.giphytest;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

public class FavoriteDataSource extends PageKeyedDataSource<Integer, ImageData> {


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ImageData> callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ImageData> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ImageData> callback) {

    }
}
