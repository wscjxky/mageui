package com.mage.magemata.circle;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.util.MyPrefence;
import com.mage.magemata.util.MySweetAlertDialog;
import com.vondear.rxtools.RxPhotoUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.mage.magemata.constant.Constant.POST_CIRCLE_ITEM;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.constant.Constant.VALUE_CIRCLE_ID;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.historyPost;
import static com.mage.magemata.util.PublicMethod.httpPost;

/**
 * Created by Administrator on 2017/9/13.
 */

public class AddCircleActivity extends BaseActivity {
    private     String POST_CIRCLE=ROOT_URL+"circle";


    @ViewInject(R.id.addcircle_et_title)
    EditText title;
    @ViewInject(R.id.addcircle_et_content)
    EditText content;

    @Override
    public void initData() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_addcircle);
        initToolbar("添加消息", true);
    }

    @Event(R.id.upload_iv_addimg)
    private void addImage(View view) {
        openImageIntent();

    }

    @Event(R.id.addcircle_btn_submit)
    private void submit(View view) {
        if (TextUtils.isEmpty(title.getText().toString()) || TextUtils.isEmpty(content.getText().toString())
                ||uploadimage_URL==null) {
            showErrorToast(getString(R.string.empty_warning));
        }

        else {
            getConfirmDialog().setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    post();
                    historyPost("添加了一个新圈子", getUserId());
                    finish();
                }
            })
                    .show();
        }
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AddCircleActivity.class);
        context.startActivity(intent);
    }
    private void post(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("title", title.getText().toString());
        map.put("content", content.getText().toString());
        map.put("image", uploadimage_URL);
        LOG(getUserId());
        map.put("user_id", getUserId());
        httpPost(POST_CIRCLE, map, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if (Objects.equals(result.getString("state"), "ok")){
                        showSuccToast("成功啦");
                        finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("finish",ex.toString());
                showErrorToast("失败了");

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}