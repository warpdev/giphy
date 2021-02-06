package com.warpdev.giphytest;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class SharedPreferenceManager extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    public SharedPreferenceManager(){
        mSharedPreferences = getSharedPreferences("favor",MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    public void writeData(String key, String value){
        mSharedPreferencesEditor.putString(key,value);
        mSharedPreferencesEditor.apply();
    }

    public void writeData(String key, int value) {
        mSharedPreferencesEditor.putInt(key, value);
        mSharedPreferencesEditor.apply();
    }

    public void writeData(String key, Set<String> value) {
        mSharedPreferencesEditor.putStringSet(key, value);
        mSharedPreferencesEditor.apply();
    }


    public void removeData(String key) {
        mSharedPreferencesEditor.remove(key);
        mSharedPreferencesEditor.apply();
    }

    public boolean isContain(String key) {
        return mSharedPreferences.contains(key);
    }

    public void commitData() {
        mSharedPreferencesEditor.commit();
    }
}
