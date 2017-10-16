package com.mage.magemata.chat;

import com.mage.magemata.user.User;
import com.stfalcon.chatkit.commons.models.IDialog;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * Created by troy379 on 04.04.17.
 */
public class Dialog implements IDialog<Message> ,Serializable {

    public  User user;
    public  User chatuser;

    public String dialog_id;
    public String user_id;
    public String chatuser_id;
    public String createtime;
    public String recenttime;
    public String chatuser_image;
    public String chatuser_name;

    public String dialogName;
    public ArrayList<User> users;
    public Message lastMessage;

    public int unreadCount=0;

    public Dialog(){}

    public Dialog(String id,User user,User chatuser,
                   String time) {
        this.dialog_id = id;
        this.user = user;
        this.chatuser = chatuser;
        this.chatuser_id = chatuser.user_id;
        this.chatuser_name = chatuser.name;
        this.dialogName=chatuser.name;
        this.chatuser_image = chatuser.profile;
        this.unreadCount = 0;
        this.users = new ArrayList<User>();
        this.users.add(user);
        this.users.add(chatuser);
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);
            this.lastMessage = new Message("1", user,"", date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//    public Dialog(String id,String chatuser_id, String name, String photo,
//                  User user,String time) {
//        this.dialog_id = id;
//        this.chatuser_id= chatuser_id;
//        this.chatuser_name = name;
//        this.chatuser_image = photo;
//        this.unreadCount = 0;
//        users = new ArrayList<User>();
//        users.add(user);
//        try {
//            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date = sdf.parse(time);
//            this.lastMessage = new Message("1", user,"", date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

        @Override
    public String getId() {
        return dialog_id;
    }

    @Override
    public String getDialogPhoto() {
        return chatuser_image;
    }

    @Override
    public String getDialogName() {
        return chatuser_name;
    }

    @Override
    public ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public Message getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }
}
