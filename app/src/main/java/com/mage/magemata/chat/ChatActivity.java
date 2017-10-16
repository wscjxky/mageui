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

import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.view.View.VISIBLE;
import static com.mage.magemata.constant.Constant.DIALOG_CHATUSER_ID;
import static com.mage.magemata.constant.Constant.DIALOG_CHATUSER_IMAGE;
import static com.mage.magemata.constant.Constant.DIALOG_ID;
import static com.mage.magemata.constant.Constant.DIALOG_NAME;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.httpGet;
import static com.mage.magemata.util.PublicMethod.httpPost;

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
    private String GET_MESSAGE=ROOT_URL+"message/";
    private ArrayList<Message> messagelist=new ArrayList<>();


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mychat);
        Bundle bundle=getBundle();
        dialog=(Dialog) bundle.getSerializable(DIALOG_ID);

        initToolbar(dialog.dialogName,true);
    }

    @Override
    public void initData() {
            initAdapter();
    }
    @Override
    public boolean onSubmit(CharSequence input) {
        messagesAdapter.addToStart(
                new Message("1",getUserInfo(),input.toString()),true);
        sendMessage(input.toString());
        return true;
    }

    private void sendMessage(String content) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("dialog_id",dialog.dialog_id);
        map.put("content", content);
        map.put("user_id", getUserId());
        map.put("chatuser_id", dialog.chatuser_id);
        httpPost(GET_MESSAGE, map, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    LOG(result.get("state").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LOG(ex.toString());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void Robot_Get(String content) throws IOException {
        RequestParams requestParams = new RequestParams("http://api.qingyunke.com/api.php?key=free&appid=0&msg=" + content);
        x.http().get(requestParams, new Callback.CacheCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    messagesAdapter.addToStart(
                            new Message("0", getUserInfo(), jsonObject.getString("content")), true);
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
        httpGet(GET_MESSAGE+dialog.dialog_id, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                String state= null;
                try {
                    state = result.getString("state");
                    switch (state){
                        case "ok":
                            messagelist.clear();
                            Gson gson=new Gson();
                            JSONArray jsonArray=result.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject job = jsonArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                                Message message=new Message(
                                        gson.fromJson(job.get("user").toString(),User.class),
                                        job.get("content").toString(),
                                        job.get("createtime").toString()
                                        );
                                messagelist.add(message);
                                LOG(message.getId());
                            }
                            messagesAdapter.addToEnd(messagelist,true);
                            break;
                        case "empty":
                            messagelist.clear();
                            showSuccToast(R.string.emptysearch);
                            break;
                        default:
                            LOG("error");
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("sdf",ex.toString());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public void onAddAttachments() {

    }
}
