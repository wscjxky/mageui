package com.mage.magemata.publish.joingood;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/9/27.
 */

public class AddJoinGoodActivity extends BaseActivity {
    @ViewInject(R.id.addjoingood_et_totalprice)
    private EditText prit;
    @ViewInject(R.id.addjoingood_et_shopname)
    private EditText shopname;
    @ViewInject(R.id.addjoingood_et_shopsort)
    private RadioButton shopsort;
    @ViewInject(R.id.addjoingood_et_totalprice)
    private EditText price;
    @ViewInject(R.id.addjoingood_et_content)
    private EditText content;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_addjoingood);
        initToolbar("添加凑单",true);
    }

    @Override
    public void initData() {

    }

    @Override
    public void loadData() {

    }
    @Event(R.id.addjoingood_btn_submit)
    private void submit(View view) {
        if (TextUtils.isEmpty(shopname.getText().toString()) || TextUtils.isEmpty(content.getText().toString())) {
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
