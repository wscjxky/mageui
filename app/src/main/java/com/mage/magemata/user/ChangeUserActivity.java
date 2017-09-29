package com.mage.magemata.user;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.util.MyPrefence;
import com.squareup.picasso.Picasso;
import com.vondear.rxtools.RxDataUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.historyPost;
import static com.mage.magemata.util.PublicMethod.httpPost;

/**
 * Created by Administrator on 2017/9/14.
 */

public class ChangeUserActivity extends BaseActivity {

    @ViewInject(R.id.changuser_et_username)
    EditText username;
    @ViewInject(R.id.changuser_et_school)
    EditText school;
    @ViewInject(R.id.changuser_et_phone)
    EditText phone;
    private User user;
    private String url=ROOT_URL+"user";

    @Override
    public void initData() {
        user=MyPrefence.getInstance(ChangeUserActivity.this).getUser();
        username.setText(user.name);
        school.setText(user.getCollege());
        uploadimage_URL=user.getProfile();
        phone.setText(user.getPhone());
        if(!RxDataUtils.isNullString(uploadimage_URL)) {
            Picasso.with(this)
                    .load(user.getProfile())
                    .resize(200, 200)
                    .centerCrop()
                    .into(uploadimg);
        }
    }

    @Override
    public void loadData() {
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_changuser);
        initToolbar("修改信息", true);

    }
    @Event(R.id.upload_iv_addimg)
    private void addImage(View view) {
        openImageIntent();
    }

    @Event(R.id.changuser_btn_submit)
    private void submit(View view) {
        if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(school.getText().toString())
                || uploadimage_URL == null) {
            showErrorToast(getString(R.string.empty_warning));
        } else {
            getConfirmDialog().setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    post();
                    historyPost("修改了个人信息", getUserId());
                    finish();
                }
            })
                    .show();
        }
    }
    private void post() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("account", user.account);
        map.put("user_id", getUserId());
        map.put("phone", phone.getText().toString());
        map.put("name", username.getText().toString());
        map.put("college", school.getText().toString());
        map.put("profile", uploadimage_URL);
        Log.e("image_url", uploadimage_URL);
        httpPost(url, map, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if(Objects.equals(result.getString("state"), "ok")){
                        User user=new Gson().fromJson(result.getString("data"),User.class);
                        MyPrefence.getInstance(ChangeUserActivity.this).saveUser(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showSuccToast("成功啦");
                finish();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("finish", ex.toString());
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
