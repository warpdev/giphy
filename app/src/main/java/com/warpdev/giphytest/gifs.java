package com.warpdev.giphytest;

import java.util.ArrayList;

class gif{
    private String name;
    private String id;
    private Boolean fav;

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

    public gif(String name, String id){
        this.name=name;
        this.id=id;
    }
}

public class gifs {
    private ArrayList<gif> gifs_list;

    public void add_gif(String name, String id){
        gifs_list.add(new gif(name, id));
    }

    public gifs(){
        gifs_list=new ArrayList<gif>();
    }
}
