package com.warpdev.giphytest;

import java.util.ArrayList;

class gif{
    private String name;
    private String id;
    private Boolean fav;
    private int height;

    public Boolean getFav() {
        return fav;
    }

    public void setFav(Boolean fav) {
        this.fav = fav;
    }


    public void setName(String s){
        this.name=s;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getName(){
        return this.name;
    }

    public String getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "gif{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", fav=" + fav +
                ", height=" + height +
                '}';
    }

    public gif(String name, String id, String h){
        this.name=name;
        this.id=id;
        this.fav=false;
        this.height=Integer.parseInt(h);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

public class gifs {
    private static ArrayList<gif> gifs_list;

    public void add_gif(String name, String id, String h){
        gifs_list.add(new gif(name, id,h));
    }

    public gif get_gif(int idx){
        return gifs_list.get(idx);
    }

    public ArrayList<gif> get_gif(){
        return gifs_list;
    }

    public int get_size(){
        return gifs_list.size();
    }

    public gifs(){
        gifs_list=new ArrayList<gif>();
    }
}