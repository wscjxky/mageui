package com.mage.magemata.chat;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.mage.magemata.R;
import com.mage.magemata.main.BaseFragment;
import com.mage.magemata.user.User;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/4/24.
 */
@ContentView(R.layout.fragment_mychat)
public class ChatFragment extends BaseFragment implements DialogsListAdapter.OnDialogClickListener<Dialog>, DialogsListAdapter.OnDialogLongClickListener<Dialog>  {
    @ViewInject(R.id.mychat_dialoglist)
    private  DialogsList dialogsList;
    public  static    User NEWUSER;
    public  static    User REUSER;

    private DialogsListAdapter<Dialog> dialogsAdapter;
    private ImageLoader imageLoader;

    @Override
    protected void initData() {
        NEWUSER = new User("2","我是卖家","http://img.qq745.com/uploads/allimg/141231/1-1412310J544-51.jpg");
        REUSER = new User("1","我是","http://img.qq745.com/uploads/allimg/141231/1-1412310J544-51.jpg");

        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(mAppCompatActivity).load(url).into(imageView);
            }
        };
        dialogsAdapter = new DialogsListAdapter<>(R.layout.chat_item,imageLoader);
        dialogsAdapter.setItems(getDialogs());

        dialogsAdapter.setOnDialogClickListener(this);
        dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(dialogsAdapter);
    }

    @Override
    protected void setData() {

    }


    private  ArrayList<Dialog> getDialogs() {
        ArrayList<Dialog> chats = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        users.add(NEWUSER);
        Dialog dialog2 = new Dialog("0", "我是小骆", "http://img.qq745.com/uploads/allimg/141231/1-1412310J544-51.jpg", users, new Message("1", NEWUSER, "第一个单聊测试"), 2);
        chats.add(dialog2);
        chats.add(dialog2);

        chats.add(dialog2);
        return chats;
    }

    //for example
    private void onNewMessage(String dialogId, Message message) {
        boolean isUpdated = dialogsAdapter.updateDialogWithMessage(dialogId, message);
        if (!isUpdated) {
            //Dialog with this ID doesn't exist, so you can create new Dialog or update all dialogs list
        }
    }

    //for example
    public void onNewDialog(Dialog dialog) {
        dialogsAdapter.addItem(dialog);
        dialogsAdapter.notifyDataSetChanged();
    }
    @Override
    public void onDialogClick(Dialog dialog) {
//        EventBus.getDefault().postSticky(dialog);
        readyGo(ChatActivity.class);
    }
    @Override
    public void onDialogLongClick(Dialog dialog) {
        Toast.makeText(mAppCompatActivity,"长点击",Toast.LENGTH_SHORT).show();
    }
}
