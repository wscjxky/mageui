package com.mage.magemata.publish.joingood;

import android.graphics.Bitmap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/5/30.
 */

public class JoinGoodCustomer {
    private String userName;
    private String content;
    private String createdAt;
    private Bitmap userPortrait;
    private int currentPrice;
    public int getCurrentPrice(){
        return currentPrice;
    }

    public String getContent( ) {
        return content;
    }
    public void setContent(String content ) {
        this.content= content;
    }
    public void setUserPortrait(Bitmap userPortrait) {
        this.userPortrait=userPortrait    ;
    }

    public Bitmap getUserPortrait( ) {
        return userPortrait;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public boolean isNumber(){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(this.content);
        if(isNum.matches())
            currentPrice=Integer.valueOf(this.content);
        return isNum.matches();
    }

}
