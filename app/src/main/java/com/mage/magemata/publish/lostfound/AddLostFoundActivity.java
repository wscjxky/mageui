package com.mage.magemata.publish.lostfound;

import android.content.Context;
import android.content.Intent;
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

import static com.mage.magemata.util.PublicMethod.httpPost;

/**
 * Created by Administrator on 2017/9/13.
 */

public class AddLostFoundActivity extends BaseActivity {
    private String url = "";
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
        setContentView(R.layout.activity_addlostfound);
        initToolbar("添加消息", true);
    }


    @Event(R.id.addcircle_btn_submit)
    private void submit(View view) {
        if (TextUtils.isEmpty(title.getText().toString()) || TextUtils.isEmpty(content.getText().toString())) {
            showErrorToast("不可为空哦");
        }
        else {
            showConfirmDialog("确认提交吗?", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }
    }
}