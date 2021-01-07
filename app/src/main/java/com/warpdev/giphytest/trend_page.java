package com.warpdev.giphytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

public class trend_page extends AppCompatActivity {

    private JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_page);
        Log.d("test","hello1");
        contactAPI con_api = new contactAPI();
        con_api.execute(getString(R.string.gif_api_key));
    }
}