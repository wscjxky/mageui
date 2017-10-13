package com.mage.magemata.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.user.User;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.Objects;

import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2017/9/28.
 */

public class ChatActivity extends BaseActivity implements MessageInput.InputListener  ,MessageInput.AttachmentsListener{
    protected MessagesListAdapter<Message> messagesAdapter;
    @ViewInject(R.id.mychat_messagesList)
    private MessagesList messagesList;
    @ViewInject(R.id.mychat_input)
    private MessageInput messageInput;
    private  Dialog dialog;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mychat);
        initToolbar("聊天界面",true);
    }

    @Override
    public void initData() {
            initAdapter();
    }
    @Override
    public boolean onSubmit(CharSequence input) {
        messagesAdapter.addToStart(
                new Message("0", new User("0","sdaf","http://47.94.251.202/mage/public/uploads/-1811356438.jpg"), input.toString()),true);
        try {
            Robot_Get(input.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void Robot_Get(String content) throws IOException {
        RequestParams requestParams = new RequestParams("http://api.qingyunke.com/api.php?key=free&appid=0&msg=" + content);
        x.http().get(requestParams, new Callback.CacheCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    messagesAdapter.addToStart(
                            new Message("1", getUserInfo(), jsonObject.getString("content")), true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }

            @Override
            public boolean onCache(JSONObject result) {
                return false;
            }
        });
    }

    private void initAdapter() {
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(ChatActivity.this).load(url).into(imageView);
            }
        };
        messagesAdapter = new MessagesListAdapter<>(getUserId(), imageLoader);
        messagesList.setAdapter(messagesAdapter);
        messageInput.setInputListener(this);
        messageInput.setAttachmentsListener(this);
    }
    @Override
    public void loadData() {

    }

    @Override
    public void onAddAttachments() {

    }
}
