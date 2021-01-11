package com.warpdev.giphytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.security.SecurityPermission;
import java.util.LinkedHashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("favor",Context.MODE_PRIVATE);
        SharedPreferences.Editor SP_editor = sharedPreferences.edit();

        if(!sharedPreferences.contains("favorlist")){
            LinkedHashSet<String> t_set = new LinkedHashSet<>();
            SP_editor.putStringSet("favorlist",t_set);
            SP_editor.apply();
        }
        SP_editor.commit();

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