package com.mage.magemata.circle;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/9/5.
 */

public class Circle {
    private int circle_id;

    private String title;
    private String createTime;
    private String content;
    private String people_count;
    private Boolean  subs= false;
    private String image;
    public void setCircle_id(int circle_id) {
        this.circle_id=circle_id    ;
    }
    public int getCircle_id( ) {
        return circle_id;
    }
    public void setIntroduction(String content) {
        this.content=content    ;
    }
    public String getContent( ) {
        return content;
    }
    public String getTitle( ) {
        return title;
    }
    public void setTitle(String title ) {
        this.title= title;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image=image;
    }
}
