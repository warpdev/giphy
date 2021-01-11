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

    private SharedPreferences sharedPreferences;
    private RecyclerView favor_rec;
    private gifs favor_list;
    private Set<String> favor_set;
    private giflist_adapter list_adapter;
    private RecyclerView.LayoutManager LM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor_page);
        sharedPreferences=getSharedPreferences("favor",Context.MODE_PRIVATE);
        SharedPreferences.Editor SP_editor = sharedPreferences.edit();
        favor_list=new gifs();
        favor_set=sharedPreferences.getStringSet("favorlist",null);
        if(favor_set!=null){
            Iterator<String> iterator = favor_set.iterator();
            while(iterator.hasNext()){
                String t_id = iterator.next();
                Log.e("favor",t_id+", url : "+sharedPreferences.getString(t_id,null)+", h : "+sharedPreferences.getInt(t_id+"_h",0)+", w : "+sharedPreferences.getInt(t_id+"_w",0));
                favor_list.add_gif(sharedPreferences.getString(t_id,null),t_id,sharedPreferences.getInt(t_id+"_h",0),sharedPreferences.getInt(t_id+"_w",0),true);
            }
        }
        favor_rec=findViewById(R.id.favor_rec);
        list_adapter= new giflist_adapter(favor_list,sharedPreferences);
        LM = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        LM.setItemPrefetchEnabled(true);
        favor_rec.setAdapter(list_adapter);
        favor_rec.setLayoutManager(LM);
        list_adapter.notifyDataSetChanged();
        Log.e("test","Test");



    }
}