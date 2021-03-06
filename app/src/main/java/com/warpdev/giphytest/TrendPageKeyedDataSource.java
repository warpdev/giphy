package com.warpdev.giphytest;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * TrendGif들에 대한 DataSource
 * 한 페이지당 25개로 설정하고 0번부터 불러온다.
 *
 * @author warpdev
 */
public class TrendPageKeyedDataSource extends PageKeyedDataSource<Integer, ImageData> {

    /** 불러온 이미지들에 대한 IdSets */
    private static HashSet<String> mIdSets;    //실시간 trend 위치 변동시에 두개가 나오는 문제 해결용

    public TrendPageKeyedDataSource(){
        mIdSets = new HashSet<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ImageData> callback) {

        //불러온 데이터를 넣어줄 ArrayList
        ArrayList<ImageData> gifList=new ArrayList<>();

        //데이터를 불러오기위한 object
        ApiConnectionCreator apiConnectionCreator = new ApiConnectionCreator();

        //getList(Api키, 데이터를 불러올 시작 위치, 데이터를 가져오는 갯수)
        Call<TrendingResult> trendingResults = apiConnectionCreator.getTrendingInterface().getList("tl2VpgSXvm9XutwI0GlNZec0XcGqhnJx", 0, 25);

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
                        gifList.add(imageData);
                        mIdSets.add(imageData.getId());
                    }
                    callback.onResult(gifList,null,25);

                } else {
                    //정상적으로 값을 받아오지 못한 경우에 처리를 해야하지만 아직까지는 발견된 경우가 없음.
                }
            }

            @Override
            public void onFailure(Call<TrendingResult> call, Throwable t) {
                //사용자의 네트워크에 문제가 있는 경우
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ImageData> callback) {
        // loadInit에서 불러오는게 0번 위치부터 불러오므로 이전 페이지에 대한 로드는 필요하지 않다.
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ImageData> callback) {

        //불러온 데이터를 넣어줄 ArrayList
        ArrayList<ImageData> gifList= new ArrayList<>();

        //데이터를 불러오기위한 object
        ApiConnectionCreator apiConnectionCreator = new ApiConnectionCreator();

        //getList(Api키, 데이터를 불러올 시작 위치, 데이터를 가져오는 갯수)
        Call<TrendingResult> trendingResults = apiConnectionCreator.getTrendingInterface().getList("tl2VpgSXvm9XutwI0GlNZec0XcGqhnJx", params.key, 25);
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
                        gifList.add(imageData);
                        mIdSets.add(imageData.getId());
                    }
                    callback.onResult(gifList,params.key+25);
                } else {
                    //정상적으로 값을 받아오지 못한 경우에 처리를 해야하지만 아직까지는 발견된 경우가 없음.
                }
            }

            @Override
            public void onFailure(Call<TrendingResult> call, Throwable t) {
                //사용자의 네트워크에 문제가 있는 경우
            }
        });


    }

}
