package com.mage.magemata.user;

import com.stfalcon.chatkit.commons.models.IUser;

/**
 * Created by Administrator on 2017/9/12.
 */

public class User  implements IUser  {
    @Override
    public String getId() {
        return user_id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return profile;
    }

    public User(String id, String name, String avatar) {
        this.user_id = id;
        this.name = name;
        this.profile = avatar;
    }





    public String username,password,name,phone,profile,gender,college;
    public String content;
    public String createtime;
    public String user_id;

    public String getContent() {
        return content;
    }
    public String getTime() {
        return createtime;
    }
//登陆用的


        public String getPhone() {
            return phone;
        }


        public String  getProfile() {
            return profile;
        }
        public String  getuserName() {
            return name;
        }
    public String  getCollege() {
        return college;
    }

}
