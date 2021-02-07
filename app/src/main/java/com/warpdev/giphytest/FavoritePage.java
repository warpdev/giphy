package com.warpdev.giphytest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Set;

/**
 * Favorite한 gif들을 표시하는 액티비티
 * SharedPreferences에 저장되어 있는 목록을 이용하여 ImageData로 이루어진 ArrayList를 구성하여 RecyclerView에 띄운다.
 * 인터넷 연결이 없는 경우에도 캐시에 남아있는 데이터로 이미지 로드가 되는 경우가 있기 때문에 연결 여부에 대한 특별한 처리를 하지는 않음.
 *
 * @author warpdev
 */
public class FavoritePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor_page);

        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager();

        //Gif에 대한 정보들이 담길 ArrayList
        ArrayList<ImageData> favoriteImageList = new ArrayList<>();
        //SharedPreferences 에서 Favorite한 목록을 가져온다.
        Set<String> favoriteSets = sharedPreferenceManager.getStringSet("favorlist");

        if (favoriteSets != null) {
            //SharedPreferences 에 있는 정보를 ArrayList에 담는다.
            for (String tId : favoriteSets) {
                ImageData.GifImage.AboutGif aboutGif = new ImageData.GifImage.AboutGif(
                        sharedPreferenceManager.getInt(tId + "_h"),
                        sharedPreferenceManager.getInt(tId + "_w"),
                        sharedPreferenceManager.getString(tId));
                ImageData imageData = new ImageData(tId, aboutGif, true);
                favoriteImageList.add(imageData);
            }
        }
        RecyclerView favoriteRecyclerView = findViewById(R.id.favor_rec);
        FavoriteListAdapter gifListAdapter = new FavoriteListAdapter(favoriteImageList);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setItemPrefetchEnabled(true);
        favoriteRecyclerView.setAdapter(gifListAdapter);
        favoriteRecyclerView.setLayoutManager(layoutManager);
        gifListAdapter.notifyDataSetChanged();
    }
}