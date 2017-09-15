package com.mage.magemata.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mage.magemata.user.User;

/**
 * Created by Administrator on 2017/9/12.
 */
public class MyPrefence {
    private static final String PREFENCE_NAME = "library";
    private static MyPrefence mPrefence;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private static final String KEY_IS_LOGIN = "KEY_IS_LOGIN";
    private static final String KEY_USER_INFO = "KEY_USER_INFO";


    private MyPrefence(Context context){
        mSharedPreferences=context.getSharedPreferences(PREFENCE_NAME,Context.MODE_PRIVATE);
        mEditor=mSharedPreferences.edit();
    }

    public synchronized static MyPrefence getInstance(Context context) {
        if ( mPrefence== null) {
            mPrefence = new MyPrefence(context);
        }
        return mPrefence;
    }

    public String getString(String tag){
        return  mSharedPreferences.getString(tag,null);
    }

    public void saveString(String tag,String value){
        mEditor.putString(tag,value).apply();
    }

    public int getInt(String tag){
        return mSharedPreferences.getInt(tag,-1);
    }

    public int getInt(String tag,int def){
        return mSharedPreferences.getInt(tag,def);
    }

    public void saveInt(String tag,int value){
        mEditor.putInt(tag,value).apply();
    }

    public boolean getBool(String tag){
        return mSharedPreferences.getBoolean(tag,false);
    }

    public void saveBool(String tag,boolean value){
        mEditor.putBoolean(tag,value).apply();
    }



    //登录成功后保存用户信息
    public void saveUser(User user ){
        saveBool(KEY_IS_LOGIN,true);
        saveString(KEY_USER_INFO,new Gson().toJson(user));
    }

    public User getUser(){
        String s=getString(KEY_USER_INFO);
        if (s == null) {
            return null;
        }else {
            return new Gson().fromJson(s, User.class);
        }
    }
    public int getUserId(){
        String s=getString(KEY_USER_INFO);
        if (s == null) {
            return 0;
        }else {
            return new Gson().fromJson(s, User.class).getId();
        }
    }

    //是否登录
    public boolean isLogined(){
        return getBool(KEY_IS_LOGIN);
    }

    //退出登录
    public void logOut(){
        saveBool(KEY_IS_LOGIN,false);
        mEditor.remove(KEY_USER_INFO)
                .apply();
    }
}