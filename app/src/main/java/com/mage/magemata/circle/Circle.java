package com.mage.magemata.circle;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/9/5.
 */

public class Circle {
    private String title;
    private String createTime;
    private String introduction;
    private String people_count;
    private Boolean  subs= false;
    private Bitmap logo;

    public void setIntroduction(String introduction) {
        this.introduction=introduction    ;
    }
    public String getIntroduction( ) {
        return introduction;
    }
    public String getTitle( ) {
        return title;
    }
    public void setTitle(String title ) {
        this.title= title;
    }
    public void subs() {
        this.subs=true    ;
    }
    public void unsubs() {
        this.subs=false    ;
    }

    public Bitmap getLogo() {
        return logo;
    }
    public void setLogo(Bitmap bitmap) {
        this.logo=bitmap;
    }
}
