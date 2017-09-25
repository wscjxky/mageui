package com.mage.magemata.constant;

import com.mage.magemata.util.MyPrefence;

/**
 * Created by Administrator on 2017/9/12.
 */

public class Constant {
    public  static  String UPLOAD="http://47.94.251.202/mage/public/index.php/upload";
    public  static  String SHOWIMAGE="http://47.94.251.202/mage/public/";

    public  static  String ROOT_URL="http://47.94.251.202/mage/public/index.php/";

    public  static  String USER=ROOT_URL+"user/";
    public  static  String GET_USERLOGIN=USER+"login/";
    public  static  String POST_USERREGISET=USER;



    public  static  String GET_CIRCLE=ROOT_URL+"circle";
    public  static  String GET_CIRCLE_ITEM=GET_CIRCLE+"item";
    public  static  String GET_CIRCLE_ITEM_COMMENT=GET_CIRCLE_ITEM+"comment";


    public  static  String POST_CIRCLE_ITEM_COMMENT=GET_CIRCLE_ITEM+"comment";
    public  static  String POST_CIRCLE_ITEM=GET_CIRCLE+"item";
    public  static  String POST_CIRCLE=ROOT_URL+"circle";

    public static final String T_USER_ID = "USER_ID";
    public static final String CIRCLE_ID = "CIRCLE_ID";
    public static final String CIRCLE_ITEM_ID = "CIRCLE_ITEM_ID";
    public static final String GOOD_ID = "good_id";

    public static String VALUE_CIRCLE_ID;

    //数据库字段
    public static final String FLASH_USER_ID="flash_user_id";

    /**
     * user表
     */
    //变换的观察userid
    public static final String USER_ID="user_id";

    /**
     * cicle表
     */
    public static final String T_CIRCLE_ID="circle_id";




    public static final String USER_FOLLOW="userfollow";
    public static final String FOLLOW_USER_ID="follow_user_id";

}
