package com.warpdev.giphytest;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.Switch;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.net.SocketPermission;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.Set;

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

    private static final String colors[] = {"#75FAA2", "#5ACAFA", "#8D41F6", "#ED706B", "#FDF276"};
    private gifs gifs_list;
    private SharedPreferences sharedPreferences;
    private Set<String> id_sets;
    private int color_param;
    public giflist_adapter(gifs gifs_list, SharedPreferences sharedPreferences){
        this.gifs_list=gifs_list;
        this.sharedPreferences=sharedPreferences;
        this.color_param=0;
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
        int w=gifs_list.get_gif(position).getWidth();
        int h=gifs_list.get_gif(position).getHeight();
        Bitmap bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor(colors[(color_param++)%5]));
        Drawable drawable = new BitmapDrawable(null,bitmap);
        Glide.with(viewHolder.GifView).load(turi).placeholder(drawable).override(w,h).into(viewHolder.GifView);
        viewHolder.aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {  //favorite 스위치 클릭할때
            SharedPreferences.Editor SP_editor = sharedPreferences.edit();

            for(int i = 0; i<gifs_list.get_size(); i++){
                gifs_list.get_gif(position).setFav(isChecked);
                if(gifs_list.get_gif(i).getFav()){
                    id_sets=sharedPreferences.getStringSet("favorlist",null);   //favorlist : favor누른것들의 id집합
                    if(id_sets!=null){
                        id_sets.add(gifs_list.get_gif(i).getId());
                    }

                    SP_editor.putString(gifs_list.get_gif(i).getId(),gifs_list.get_gif(i).getUrl());    //id : url 저장
                    SP_editor.putInt(gifs_list.get_gif(i).getId()+"_w",gifs_list.get_gif(i).getWidth());    // (id)_w : width 저장
                    SP_editor.putInt(gifs_list.get_gif(i).getId()+"_h",gifs_list.get_gif(i).getHeight());   // (id)_h : height 저장
                    SP_editor.apply();
                }
                else{
                    if(sharedPreferences.contains(gifs_list.get_gif(i).getId())){

                        id_sets=sharedPreferences.getStringSet("favorlist",null);

                        if(id_sets!=null){
                            id_sets.remove(gifs_list.get_gif(i).getId());
                            SP_editor.remove("favorlist");
                            SP_editor.putStringSet("favorlist",id_sets);    //favorlist 갱신하기
                        }

                        SP_editor.remove(gifs_list.get_gif(i).getId());
                        SP_editor.remove(gifs_list.get_gif(i).getId()+"_w");
                        SP_editor.remove(gifs_list.get_gif(i).getId()+"_h");
                        SP_editor.apply();
                    }
                }
            }
            SP_editor.commit();
        });
        viewHolder.aSwitch.setChecked(gifs_list.get_gif(position).getFav());
    }

    @Override
    public int getItemCount() {
        return gifs_list.get_size();
    }
}
