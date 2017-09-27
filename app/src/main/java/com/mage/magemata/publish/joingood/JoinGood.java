package com.mage.magemata.publish.joingood;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

/**
 * Created by Administrator on 2017/9/26.
 */

public class JoinGood {
    private boolean sticked=false;
    private String introduction;
    private String shopName;
    private String shopSort;
    private String totalPrice="0";
    private String  currentPrice="0";
    private Bitmap shopLogo;

    public void stickedTotrue(){
        this.sticked=true;
    }
    public boolean isSticked(){
        return sticked;
    }
    public void setIntroduction(String introduction) {
        this.introduction=introduction    ;
    }
    public String getIntroduction( ) {
        return introduction;
    }
    public String getShopname( ) {
        return shopName;
    }
    public void setShopname(String shopName ) {
        this.shopName= shopName;
    }
    public void setShopsort(String shopSort) {
        this.shopSort=shopSort    ;
    }
    public String getShopSort( ) {
        return shopSort;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setTotalPrice(String totalPrice){
        if(Objects.equals(totalPrice, ""))
            this.totalPrice="0";
        else
            this.totalPrice=totalPrice;
    }
    public String getTotalPrice() {
        return totalPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public boolean isEnough(){
        return Integer.parseInt(this.totalPrice) - Integer.parseInt(this.currentPrice) == 0;
    }
    public String remainPrice(){
        return Integer.parseInt(this.totalPrice) - Integer.parseInt(this.currentPrice) +"";
    }
    public Bitmap getShopLogo() {
        return shopLogo;
    }
    public void setShopLogo(Bitmap bitmap) {
        this.shopLogo=bitmap;
    }
}
