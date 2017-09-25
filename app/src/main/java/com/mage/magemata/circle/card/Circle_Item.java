package com.mage.magemata.circle.card;

/**
 * Created by Administrator on 2017/9/12.
 */

class Circle_Item {
    private int circle_item_id;
    private int circle_id;
    private int user_id;

    private String title;
    private String createTime;
    private String content;
    private String people_count;
    private String image;
    private String user_image;
    public int getUserid( ) {
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
