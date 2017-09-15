package com.mage.magemata.user;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.util.MyPrefence;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.util.PublicMethod.LOG;
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
        username.setText(user.getuserName());
        school.setText(user.getCollege());
        phone.setText(user.getPhone());

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
        if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(school.getText().toString())) {
            showErrorToast("不可为空哦");
        } else {
            final SweetAlertDialog dialog=getConfirmDialog();
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("username",user.getName() );
                    map.put("user_id",user.getId()+"" );
                    map.put("phone", phone.getText().toString());
                    map.put("name", username.getText().toString());
                    map.put("college", school.getText().toString());
                    map.put("profile", uploadimage_URL);
                    Log.e("image_url",uploadimage_URL);
                    httpPost(url, map, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("result",result);
                            showSuccToast("成功啦");
                            finish();
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
            });
            dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    dialog.cancel();
                }
            });
            dialog.show();

        }
    }

}
