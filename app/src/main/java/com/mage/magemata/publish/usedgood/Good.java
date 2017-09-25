package com.mage.magemata.publish.usedgood;

import java.util.List;

/**
 * Created by Administrator on 2017/9/17.
 */



public class Good {
    private int good_id;
    private String bargin;
    private String price;

    private String good_name;
    private String createtime;
    private String content;
    private int state;
    private String user_id;
    private String phone;
    private String user_name;
    private String image;
    public int getGood_id( ) {
        return good_id;
    }
    public String getCreatetime( ) {
        return createtime;
    }
        public String getContent( ) {
            return content;
        }
        public String getGood_name( ) {
            return good_name;
        }
        public int getState( ) {
            return state;
        }
    public String getBargin( ) {
        return bargin;
    }
    public String getPrice( ) {
        return price;
    }

    public String getPhone( ) {
            return phone;
        }
        public String getUser_id( ) {
            return user_id;
        }
        public String getUser_name( ) {
            return user_name;
        }
        public String getImage() {
            return image;
        }


}
