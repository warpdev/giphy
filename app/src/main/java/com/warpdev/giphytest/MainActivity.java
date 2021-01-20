package com.warpdev.giphytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.LinkedHashSet;

/**
 * 앱을 실행하면 표시되는 MainActivity
 * onCreate에서 SharedPreferences에 favorlist의 존재를 확인하고 없다면 새로 넣어준다.
 *
 * @author warpdev
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("favor",Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

        if(!sharedPreferences.contains("favorlist")){
            LinkedHashSet<String> favorList = new LinkedHashSet<>();
            sharedPreferencesEditor.putStringSet("favorlist",favorList);
            sharedPreferencesEditor.apply();
        }
        sharedPreferencesEditor.commit();
    }


    /** Trending Gifs 버튼 클릭시 */
    public void trendingClick(View v){
        startActivity(new Intent(MainActivity.this, TrendPage.class));
    }

    /** Favorite List 버튼 클릭시 */
    public void favoriteClick(View v){
        startActivity(new Intent(MainActivity.this, FavoritePage.class));
    }
}