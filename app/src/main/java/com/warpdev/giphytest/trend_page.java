package com.warpdev.giphytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class trend_page extends AppCompatActivity {

    private JSONObject json;
    private RecyclerView rec_lef;
    private RecyclerView rec_rig;
    private RecyclerView.LayoutManager lef_layMan;
    private RecyclerView.LayoutManager rig_layMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_page);
        Log.d("test","hello1");
        contactAPI con_api = new contactAPI();
        con_api.execute(getString(R.string.gif_api_key));
    }
    class contactAPI extends AsyncTask<String, Void, Void> {

        private gifs gifs_list;

        @Override
        protected Void doInBackground(String... strings) {
            Log.d("test", "hello");
            try {
                URL trend_endpoint = new URL("https://api.giphy.com/v1/gifs/trending?" + "api_key=" + strings[0]+"&limit=5");
                HttpsURLConnection connection = (HttpsURLConnection) trend_endpoint.openConnection();
                Log.d("test", "chkkk");

                Log.d("test", "Response" + connection.getResponseCode());
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
                    gifs_list=new gifs();

                    for(int i=0; i<25; i++) {
                        gifs_list.add_gif(jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("fixed_width_small").getString("mp4"), jsonArray.getJSONObject(i).getString("id"));
                        Log.d("test",gifs_list.get_gif(i).toString());
                    }
                    Log.d("test",jsonObject.getJSONArray("data").getJSONObject(3).getString("id"));
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
            giflist_adapter list_adapter = new giflist_adapter(gifs_list);
            Log.d("test",gifs_list.get_size()+"");
            rec_lef = findViewById(R.id.rec_lef);
            rec_rig = findViewById(R.id.rec_rig);
            rec_lef.setAdapter(list_adapter);
            rec_rig.setAdapter(list_adapter);

            Log.d("test","helloooooo");
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