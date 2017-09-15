package com.mage.magemata.circle.card;

/**
 * Created by Administrator on 2017/9/12.
 */

public class Circle_Item_Comment {
    private int comment_id;
    private int circle_item_id;
    private String createtime;
    private String content;
    private String image;
    private String user_image;

    public String getTime( ) {
        return createtime;
    }
    public int getId( ) {
        return comment_id;
    }
    public String getContent( ) {
        return content;
    }
    public String getImage() {
        return  image;
    }
    public String getUser_image() {
        return  user_image;
    }

}
