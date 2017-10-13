package com.mage.magemata.chat;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mage.magemata.R;
import com.mage.magemata.circle.Circle;
import com.mage.magemata.main.BaseFragment;
import com.mage.magemata.user.User;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import static com.mage.magemata.constant.Constant.GET_CIRCLE;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.httpGet;


/**
 * Created by Administrator on 2017/4/24.
 */
@ContentView(R.layout.fragment_mychat)
public class ChatFragment extends BaseFragment implements DialogsListAdapter.OnDialogClickListener<Dialog>, DialogsListAdapter.OnDialogLongClickListener<Dialog>  {
    @ViewInject(R.id.mychat_dialoglist)
    private  DialogsList dialogsList;

    private ArrayList<Dialog> dialogslist = new ArrayList<>();
    private DialogsListAdapter<Dialog> dialogsAdapter;
    private ImageLoader imageLoader;
    private String GET_DIALOG=ROOT_URL+"dialog/";

    @Override
    protected void initData() {

        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(mAppCompatActivity).load(url).into(imageView);
            }
        };
        dialogsAdapter = new DialogsListAdapter<>(R.layout.chat_item,imageLoader);

        dialogsAdapter.setOnDialogClickListener(this);
        dialogsAdapter.setOnDialogLongClickListener(this);
        dialogsAdapter.setItems(dialogslist);

        dialogsList.setAdapter(dialogsAdapter);
    }

    @Override
    protected void setData() {
        httpGet(GET_DIALOG+getUserId(), new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                String state= null;
                try {

                    state = result.getString("state");
                    switch (state){
                        case "ok":
                            dialogslist.clear();
                            JSONArray jsonArray=result.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject job = jsonArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                                Dialog dialog=new Dialog(job.get("dialog_id").toString(),
                                        job.get("chatuser_name").toString()
                                        ,job.get("chatuser_image").toString()

                                        ,getUserInfo()
                                        ,job.get("recenttime").toString());
                                dialogslist.add(dialog);
                            }
                            break;
                        case "empty":
                            dialogslist.clear();
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
                dialogsAdapter.notifyDataSetChanged();
            }
        });

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
        readyGo(ChatActivity.class);
    }
    @Override
    public void onDialogLongClick(Dialog dialog) {
        Toast.makeText(mAppCompatActivity,"长点击",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
//            setData();
        } else {

        }
    }
}
