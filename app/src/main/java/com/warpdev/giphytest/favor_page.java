package com.warpdev.giphytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class favor_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor_page);
        SharedPreferences sharedPreferences = getSharedPreferences("favor", Context.MODE_PRIVATE);
        gifs favor_list = new gifs();
        Set<String> favor_set = sharedPreferences.getStringSet("favorlist", null);
        if(favor_set !=null){
            Iterator<String> iterator = favor_set.iterator();
            while(iterator.hasNext()){
                String t_id = iterator.next();
                favor_list.add_gif(sharedPreferences.getString(t_id,null),t_id, sharedPreferences.getInt(t_id+"_h",0), sharedPreferences.getInt(t_id+"_w",0),true);
            }
        }
        RecyclerView favor_rec = findViewById(R.id.favor_rec);
        giflist_adapter list_adapter = new giflist_adapter(favor_list, sharedPreferences);
        RecyclerView.LayoutManager LM = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        LM.setItemPrefetchEnabled(true);
        favor_rec.setAdapter(list_adapter);
        favor_rec.setLayoutManager(LM);
        list_adapter.notifyDataSetChanged();
    }
}