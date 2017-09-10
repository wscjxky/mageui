package com.mage.magemata.user;


import com.mage.magemata.R;

/**
 * Created by Administrator on 2017/5/27.
 */
public class Follower {
    private String introduction;
    private String userName;
    private String UserGrade;
    private String createdAt;
    private String  UserIntroduce;
    private int userPortrait;
    public void setUserGrade(String UserGrade) {
        this.UserGrade=UserGrade    ;
    }

    public String getUserGrade( ) {
        return UserGrade;
    }

    public String getUserIntroduce( ) {
        return UserIntroduce;
    }

    public void setUserIntroduce(String UserIntroduce ) {
        this.UserIntroduce= UserIntroduce;
    }
    public void setUserPortrait(int userPortrait) {
        this.userPortrait=userPortrait    ;
    }

    public int getUserPortrait( ) {
        return R.mipmap.logo2;
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
}
