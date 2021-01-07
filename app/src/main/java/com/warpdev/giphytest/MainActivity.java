package com.warpdev.giphytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void trending_click(View v){
        Intent i= new Intent(MainActivity.this, trend_page.class);
        startActivity(i);
    }

    public void favor_click(View v){
        Intent i = new Intent(MainActivity.this,favor_page.class);
        startActivity(i);
    }
}