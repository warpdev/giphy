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
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        list_adapter = new giflist_adapter(gifs_list,sharedPreferences);
        gif_recview=findViewById(R.id.gif_recview);
        LM = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        contactAPI con_api = new contactAPI();
        con_api.execute(getString(R.string.gif_api_key));
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

                Log.e("ck","loading : "+ loading + " pos : "+ pos+" size : "+gifs_list.get_size());

                if(!loading && staggeredGridLayoutManager!= null && pos>=gifs_list.get_size()-10){
                    Log.e("load","new");
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
                URL trend_endpoint = new URL("https://api.giphy.com/v1/gifs/trending?" + "api_key=" + strings[0]+"&offset="+gifs_list.get_size());
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


                    int t_size=gifs_list.get_size();
                    for(int i=0; i<25; i++) {
                        Log.e("add","add");
                        gifs_list.add_gif(jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("fixed_width").getString("url"), jsonArray.getJSONObject(i).getString("id"),jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("fixed_width").getString("height"));
                        Log.e("size",gifs_list.get_size()+"");
                        if(sharedPreferences.contains(gifs_list.get_gif(t_size+i).getId())){
                            Log.e("True","appear");
                            gifs_list.get_gif(t_size+i).setFav(true);
                        }
                        Log.e("test",gifs_list.get_gif(t_size+i).toString());
                    }
//                JsonReader json_reader = new JsonReader(response_reader); //아직 미완성 나중에 테스트
//                json_reader.beginObject(); // 처음 3개짜리에서
//                while (json_reader.hasNext()) {
//                    String json_key = json_reader.nextName();
//                    json_reader.beginArray();
//                    JSONObject json_object = new JSONObject();
//                    json_object.getJSONArray("data");
//                    if(json_key.equals("data")){ //data찾는데 그건 배열로 이루어져있음
//
//                    }
//                    String json_value = json_reader.nextString();
//                    Log.d("JSON", json_key + " : " + json_value);
//                }
//                json_reader.close();


//                Log.d("test",json_reader.toString());
                }
                connection.disconnect();
            } catch (Exception e) {
                System.out.println(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            Log.e("connection","finish");
            list_adapter.notifyDataSetChanged();


            loading=false;
        }



        private JSONObject read_json(JsonReader json_reader) throws Exception {
            json_reader.beginObject();
            JSONObject t_jsonobject = new JSONObject();
            while (json_reader.hasNext()) {
                String json_key = json_reader.nextName();
                String json_value = json_reader.nextString();
                Log.d("JSON", json_key + " : " + json_value);
            }

            return t_jsonobject;
        }

    }
}