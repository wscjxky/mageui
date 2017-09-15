package com.mage.magemata.user;

/**
 * Created by Administrator on 2017/9/12.
 */

public class User {
        private String username,password,name,phone,profile,gender,college;
        private int user_id;
        public int getId() {
            return user_id;
        }

        public void setId(int id) {
            user_id = id;
        }
//登陆用的
            public String getName() {
            return username;
        }

        public void setName(String name) {
            username = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

    public void setProfile(String profile) {
            this.profile = profile;
        }
}
