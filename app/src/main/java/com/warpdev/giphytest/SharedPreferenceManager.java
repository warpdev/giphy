package com.warpdev.giphytest;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

/**
 * SharedPreference를 어디에서나 사용하기 위해 만든 클래스
 * mSharedPreferences는 MainActivity에서 값을 설정해준다.
 * 기록할 정보의 유형별로 writeData메소드를 생성해준다.
 *
 * @author warpdev
 */
public class SharedPreferenceManager extends AppCompatActivity {

    public static SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    public SharedPreferenceManager(){
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

    public String getString(String key) {
        return mSharedPreferences.getString(key, null);
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public Set<String> getStringSet(String key) {
        return mSharedPreferences.getStringSet(key, null);
    }

    public boolean isContain(String key) {
        return mSharedPreferences.contains(key);
    }
}
