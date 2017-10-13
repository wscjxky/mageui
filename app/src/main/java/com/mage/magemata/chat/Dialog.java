package com.mage.magemata.chat;

import com.mage.magemata.user.User;
import com.stfalcon.chatkit.commons.models.IDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * Created by troy379 on 04.04.17.
 */
public class Dialog implements IDialog<Message> {

    public  User user;
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

    public Dialog(String id, String name, String photo,
                  User user,String time) {
        this.dialog_id = id;
        this.chatuser_name = name;
        this.chatuser_image = photo;
        this.unreadCount = 0;
        users = new ArrayList<User>();
        users.add(user);
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);
            this.lastMessage = new Message("1", user,"", date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
