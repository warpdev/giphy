package com.warpdev.giphytest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * activity_trend_page 레이아웃의 엑티비티
 * GIPHY Trending Gifs를 가져오고 RecyclerView의 infinity scroll을 구현함.
 *
 * @author warpdev
 */

public class TrendPage extends AppCompatActivity {

    /** Favorite를 기록하기 위한 SharedPreferences object */

    private GifListAdapter mGifListAdapter;
    private RecyclerView.LayoutManager mRecyclerLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_page);

        RecyclerView gifRecyclerView = findViewById(R.id.gif_recview);
        mRecyclerLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerLayoutManager.setItemPrefetchEnabled(true);

        TrendViewModel trendViewModel = new ViewModelProvider(this).get(TrendViewModel.class);

        mGifListAdapter = new GifListAdapter();
        trendViewModel.getPagedListLiveData().observe(this, imageData -> {
            mGifListAdapter.submitList(imageData);
        });

        gifRecyclerView.setAdapter(mGifListAdapter);
        gifRecyclerView.setLayoutManager(mRecyclerLayoutManager);


    }
}