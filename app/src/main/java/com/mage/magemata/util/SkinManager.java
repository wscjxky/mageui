package com.mage.magemata.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.color.CircleView;

/**
 * Created by Administrator on 2017/5/30.
 */
public class SkinManager {
    private static final String SKIN_PREF = "skinSetting" ;
    private AppCompatActivity mActivity;
    private SharedPreferences skinSettingPreference;
    private ColorDrawable colorDrawable;

    public SkinManager(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    public void setSkin(int color){
        skinSettingPreference = mActivity.getSharedPreferences(SKIN_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = skinSettingPreference.edit();
        editor.putInt("skin", color);
        editor.apply();
    }
    public void getSkin(){
        skinSettingPreference= mActivity.getSharedPreferences(SKIN_PREF,
                Context.MODE_PRIVATE);
        int color =skinSettingPreference.getInt("skin", 0);
        if (color!=0) {
            ColorDrawable colord = new ColorDrawable(color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mActivity.getWindow().setStatusBarColor(CircleView.shiftColorDown(color));
                mActivity.getWindow().setNavigationBarColor(color);
            }
            if(mActivity.getSupportActionBar()!=null)
                mActivity.getSupportActionBar().setBackgroundDrawable(colord);
        }
    }
    public int getColor(){
        skinSettingPreference= mActivity.getSharedPreferences(SKIN_PREF,
                Context.MODE_PRIVATE);
        return   skinSettingPreference.getInt("skin", 0);
    }

}
