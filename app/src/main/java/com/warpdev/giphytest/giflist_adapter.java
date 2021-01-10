package com.warpdev.giphytest;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.net.URISyntaxException;

public class giflist_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static class giflist_ViewHolder extends RecyclerView.ViewHolder {
        public ImageView GifView;
        public Switch aSwitch;

        public giflist_ViewHolder(View v) {
            super(v);
            GifView = v.findViewById(R.id.gif_tile);
            aSwitch = v.findViewById(R.id.favor_switch);

        }
    }

    private gifs gifs_list;
    private SharedPreferences sharedPreferences;
    public giflist_adapter(gifs gifs_list, SharedPreferences sharedPreferences){
        this.gifs_list=gifs_list;
        this.sharedPreferences=sharedPreferences;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gif_row_layout, parent, false);

        return new giflist_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        giflist_ViewHolder viewHolder = (giflist_ViewHolder) holder;

        Uri turi = Uri.parse(gifs_list.get_gif(position).getUrl());
        int w=viewHolder.GifView.getWidth();
        int h=(int)(gifs_list.get_gif(position).getHeight()*((double)w/200.0));
        Glide.with(viewHolder.GifView).load(turi).override(w,h).placeholder(R.drawable.ic_launcher_background).into(viewHolder.GifView);
        viewHolder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("test","changed");
                Log.e("test",isChecked+""+" pos: "+position);
                SharedPreferences.Editor SP_editor = sharedPreferences.edit();

                for(int i = 0; i<gifs_list.get_size(); i++){
                    gifs_list.get_gif(position).setFav(isChecked);
                    if(gifs_list.get_gif(i).getFav()){
                        SP_editor.putString(gifs_list.get_gif(i).getId(),gifs_list.get_gif(i).getUrl());
                    }
                    else{
                        if(sharedPreferences.contains(gifs_list.get_gif(i).getId())){
                            SP_editor.remove(gifs_list.get_gif(i).getId());
                            SP_editor.apply();
                        }
                    }
                }
                SP_editor.commit();
            }
        });

        viewHolder.aSwitch.setChecked(gifs_list.get_gif(position).getFav());
        Log.e("size","w : "+w+", h : "+h);
    }

    @Override
    public int getItemCount() {

        return gifs_list.get_size();
    }
}
