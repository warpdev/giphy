package com.warpdev.giphytest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    /** 데이터를 받아오고 있는지에 대한 여부 Flag */
    private boolean isLoading = true;

    /** 불러온 이미지들에 대한 IdSets */
    private HashSet<String> mIdSets;    //실시간 trend 위치 변동시에 두개가 나오는 문제 해결용

    /** 불러온 이미지들에 대한 정보가 담기는 ArrayList */
    private ArrayList<ImageData> mGifList;

    /** Favorite를 기록하기 위한 SharedPreferences object */
    private SharedPreferences mSharedPreferences;

    private GifListAdapter mGifListAdapter;
    private RecyclerView.LayoutManager mRecyclerLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_page);

        RecyclerView gifRecyclerView = findViewById(R.id.gif_recview);
        mRecyclerLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerLayoutManager.setItemPrefetchEnabled(true);
        mSharedPreferences = getSharedPreferences("favor", Context.MODE_PRIVATE);
        mIdSets = new HashSet<>();
        mGifList = new ArrayList<>();
        mGifListAdapter = new GifListAdapter(mGifList, mSharedPreferences);

        readData();
        gifRecyclerView.setAdapter(mGifListAdapter);
        gifRecyclerView.setLayoutManager(mRecyclerLayoutManager);

        // RecyclerView에 대한 스크롤 리스너
        gifRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] lastVisItems = ((StaggeredGridLayoutManager) mRecyclerLayoutManager).findLastVisibleItemPositions(null);
                int pos = 0;
                for (int lastVisItem : lastVisItems) {
                    if (pos < lastVisItem) {
                        pos = lastVisItem;
                    }
                }

                if (!isLoading && pos >= mGifList.size() - getResources().getInteger(R.integer.refresh_count)) {  //마지막 아이템에서 refresh_count만큼 떨어져있는 아이템을 보고있을때 미리 서버에서 데이터 가져옴
                    isLoading = true;
                    readData();
                }

            }
        });
    }

    /**
     * retrofit을 이용하여 GIPHY Trending 이미지 정보들을 Json형태로 가져온다.
     * 자동으로 TrendingResult형태로 파싱됨
     */
    public void readData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.giphy.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TrendingInterface trendingInterface = retrofit.create(TrendingInterface.class);

        //getList(Api키, 데이터를 불러올 시작 위치, 데이터를 가져오는 갯수)
        Call<TrendingResult> trendingResults = trendingInterface.getList(getString(R.string.gif_api_key), mGifList.size(), getResources().getInteger(R.integer.limit));

        trendingResults.enqueue(new Callback<TrendingResult>() {

            /** 연결 성공시 처리 */
            @Override
            public void onResponse(Call<TrendingResult> call, Response<TrendingResult> response) {
                if(response.isSuccessful()) {
                    //정상적으로 통신에 성공했을 경우
                    TrendingResult trendingResult = response.body();
                    for (ImageData imageData : trendingResult.getData()) {
                        //실시간 랭킹 변동에 의해 중복된 id 가 있는지 체크
                        if (mIdSets.contains(imageData.getId())) {
                            continue;
                        }

                        imageData.setAboutGif(
                                // preview_gif가 있다면 preview에 대한 정보, 없다면 original에 대한 정보 저장
                                (imageData.getImages().getPreviewGif().getUrl() != null ? imageData.getImages().getPreviewGif() : imageData.getImages().getOriginal())
                        );
                        //Favorite 목록인지에 대한 정보 저장
                        imageData.setFavorite(mSharedPreferences.contains(imageData.getId()));
                        mGifList.add(imageData);
                        mIdSets.add(imageData.getId());
                        mGifListAdapter.notifyDataSetChanged();
                    }
                } else {
                    //정상적으로 값을 받아오지 못한 경우에 처리를 해야하지만 아직까지는 발견된 경우가 없음.
                }
            }

            /** 네트워크 연결 실패시 알림을 띄움 */
            @Override
            public void onFailure(Call<TrendingResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "네트워크 연결을 확인해주세요", Toast.LENGTH_LONG).show();
            }
        });

        isLoading = false;
    }
}