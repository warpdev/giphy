package com.warpdev.giphytest;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public giflist_adapter(gifs gifs_list){
        this.gifs_list=gifs_list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gif_row_layout, parent, false);

        return new giflist_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        giflist_ViewHolder viewHolder = (giflist_ViewHolder) holder;

        Uri turi = Uri.parse(gifs_list.get_gif(position).getName());
        Log.d("uri",turi.toString());
        int w=viewHolder.GifView.getWidth();
        int h=(int)(gifs_list.get_gif(position).getHeight()*((double)w/200.0));
        Glide.with(viewHolder.GifView).load(turi).override(w,h).into(viewHolder.GifView);

        Log.d("size","w : "+w+", h : "+h);
    }

    @Override
    public int getItemCount() {

        return gifs_list.get_size();
    }
}
