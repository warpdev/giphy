package com.warpdev.giphytest;

import java.util.ArrayList;

class gif{
    private String url;
    private String id;
    private Boolean fav;
    private int height;
    private int width;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Boolean getFav() {
        return fav;
    }

    public void setFav(Boolean fav) {
        this.fav = fav;
    }


    public void setId(String id){
        this.id=id;
    }

    public String getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "gif{" +
                "name='" + url + '\'' +
                ", id='" + id + '\'' +
                ", fav=" + fav +
                ", height=" + height +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public gif(String url, String id, String h, String w, boolean fav){
        this.url=url;
        this.id=id;
        this.fav=fav;
        this.height=Integer.parseInt(h);
        this.width=Integer.parseInt(w);
    }

    public gif(String url, String id, int h, int w, boolean fav){
        this.url=url;
        this.id=id;
        this.fav=fav;
        this.height=h;
        this.width=w;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

public class gifs {
    private ArrayList<gif> gifs_list;

    public void add_gif(String url, String id, String h,String w,boolean fav){
        gifs_list.add(new gif(url, id,h,w,fav));
    }

    public void add_gif(String url, String id, int h, int w, boolean fav){
        gifs_list.add(new gif(url, id,h,w,fav));
    }

    public gif get_gif(int idx){
        return gifs_list.get(idx);
    }

    public ArrayList<gif> get_gif(){
        return gifs_list;
    }

    public int get_size(){
        if(gifs_list==null){
            return 0;
        }
        return gifs_list.size();
    }


    public gifs(){
        gifs_list=new ArrayList<>();
    }
}
