package com.warpdev.giphytest;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;
import java.util.Set;

/**
 *
 */
public class FavoriteDataSource extends PageKeyedDataSource<Integer, ImageData> {

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ImageData> callback) {
        ArrayList<ImageData> favoriteImageList = new ArrayList<>();
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager();
        Set<String> favoriteSets = sharedPreferenceManager.getStringSet("favorlist");


        if (favoriteSets != null) {
            //SharedPreferences 에 있는 정보를 ArrayList에 담는다.
            if(favoriteSets.size() < 25) {
                for (String tId : favoriteSets) {
                    ImageData.GifImage.AboutGif aboutGif = new ImageData.GifImage.AboutGif(
                            sharedPreferenceManager.getInt(tId + "_h"),
                            sharedPreferenceManager.getInt(tId + "_w"),
                            sharedPreferenceManager.getString(tId));
                    ImageData imageData = new ImageData(tId, aboutGif, true);
                    favoriteImageList.add(imageData);
                }
            } else {
                for (int i = 0; i<25; i++) {
                }
            }
        }


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ImageData> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ImageData> callback) {

    }
}
