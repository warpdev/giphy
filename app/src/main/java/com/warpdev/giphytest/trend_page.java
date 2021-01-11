package com.warpdev.giphytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class trend_page extends AppCompatActivity {

    private RecyclerView gif_recview;
    private boolean loading=true;
    private gifs gifs_list;
    private SharedPreferences sharedPreferences;
    private giflist_adapter list_adapter;
    private RecyclerView.LayoutManager LM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_page);
        gifs_list=new gifs();
        sharedPreferences = getSharedPreferences("favor",Context.MODE_PRIVATE);
        list_adapter = new giflist_adapter(gifs_list,sharedPreferences);
        gif_recview=findViewById(R.id.gif_recview);
        LM = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        LM.setItemPrefetchEnabled(true);
        read_data();
        gif_recview.setAdapter(list_adapter);
        gif_recview.setLayoutManager(LM);
        gif_recview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] lastVisItems=staggeredGridLayoutManager.findLastVisibleItemPositions(null);
                int pos=0;
                for(int i= 0; i<lastVisItems.length; i++){
                    if(pos<lastVisItems[i]){
                        pos=lastVisItems[i];
                    }
                }

                if(!loading && pos>=gifs_list.get_size()-getResources().getInteger(R.integer.refresh_count)){  //마지막 아이템에서 refresh_count만큼 떨어져있는 아이템을 보고있을때 미리 서버에서 데이터 가져옴
                    loading=true;
                    read_data();

                }

            }
        });
    }
    public void read_data(){
        contactAPI con_api = new contactAPI();
        con_api.execute(getString(R.string.gif_api_key));
    }


    class contactAPI extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL trend_endpoint = new URL("https://api.giphy.com/v1/gifs/trending?" + "api_key=" + strings[0]+"&offset="+gifs_list.get_size()+"&limit="+getResources().getInteger(R.integer.limit));
                HttpsURLConnection connection = (HttpsURLConnection) trend_endpoint.openConnection();

                if (connection.getResponseCode() == 200) {
                    InputStream response_data = connection.getInputStream();
                    InputStreamReader response_reader = new InputStreamReader(response_data, "UTF-8");
                    BufferedReader buff_reader = new BufferedReader(response_reader, 8*1024); //스트링버퍼로 처리후에 jsonobject로 파싱해서 쓰는방법으로 처리
                    String line = null;
                    StringBuffer sb = new StringBuffer();
                    while((line = buff_reader.readLine()) != null){
                        sb.append(line+"\n");
                    }
                    JSONObject jsonObject = new JSONObject(sb.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    int t_size = jsonArray.length();
                    for(int i=0; i<t_size; i++) {
                        try {
                            gifs_list.add_gif(jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("preview_gif").getString("url"),
                                    jsonArray.getJSONObject(i).getString("id"),
                                    jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("preview_gif").getString("height"),
                                    jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("preview_gif").getString("width"),
                                    sharedPreferences.contains(jsonArray.getJSONObject(i).getString("id")));
                        }catch (JSONException e){   //preivew가 없는경우에 original로 불러오기
                            gifs_list.add_gif(jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("original").getString("url"),
                                    jsonArray.getJSONObject(i).getString("id"),
                                    jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("original").getString("height"),
                                    jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("original").getString("width"),
                                    sharedPreferences.contains(jsonArray.getJSONObject(i).getString("id")));
                        }
                    }
                }
                connection.disconnect();
            } catch (Exception e) {
                Log.e("error",e.toString());
                System.out.println(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            list_adapter.notifyDataSetChanged();
            loading=false;
        }


    }
}