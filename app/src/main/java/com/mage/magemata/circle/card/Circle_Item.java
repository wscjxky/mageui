package com.mage.magemata.circle.card;

/**
 * Created by Administrator on 2017/9/12.
 */

class Circle_Item {
    public int circle_item_id;
    public int circle_id;
    public String user_id;

    public String title;
    public String createTime;
    public String content;
    public String people_count;
    public String image;
    public String user_image;
    public String getUserid( ) {
        return user_id;
    }
    public int getId( ) {
        return circle_item_id;
    }
    public String getContent( ) {
        return content;
    }
    public String getCreateTime( ) {
        return createTime;
    }
    public String getTitle( ) {
        return title;
    }
    public String getImage() {
        return  image;
    }

    public String getUserImage() {
        return  user_image;
    }
}
