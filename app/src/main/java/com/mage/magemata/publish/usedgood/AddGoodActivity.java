package com.mage.magemata.publish.usedgood;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mage.magemata.R;
import com.mage.magemata.circle.AddCircleActivity;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.util.MyPrefence;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.util.PublicMethod.historyPost;
import static com.mage.magemata.util.PublicMethod.httpPost;

/**
 * Created by Administrator on 2017/9/17.
 */

public class AddGoodActivity  extends BaseActivity {
    @ViewInject(R.id.addgood_content)
    private EditText content;
    @ViewInject(R.id.addgood_good_name)
    private EditText name;
    @ViewInject(R.id.addgood_rb_bargin)
    private RadioButton bargin;
    @ViewInject(R.id.addgood_price)
    private EditText price;
    @ViewInject(R.id.addgood_phone)
    private EditText phone;
    private String POST_URL = ROOT_URL + "good";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_addgood);
        initToolbar("添加商品", true);

    }

    @Override
    public void initData() {

    }

    @Override
    public void loadData() {

    }

    @Event(R.id.addgood_btn_submit)
    private void submit(View view) {
        if (TextUtils.isEmpty(content.getText().toString()) || TextUtils.isEmpty(name.getText().toString())) {
            showErrorToast("不可为空哦");
        } else {
            getConfirmDialog().setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    post();
                    finish();
                }
            })
                    .show();
        }
    }

    private void post() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("good_name", name.getText().toString());
        map.put("content", content.getText().toString());
        map.put("price", price.getText().toString());
        map.put("phone", phone.getText().toString());
        map.put("image", uploadimage_URL);
        map.put("user_id", getUserInfo().user_id);
        map.put("user_name", getUserInfo().name);
        map.put("bargin", bargin.isChecked() ? "不支持" : "支持");
        httpPost(POST_URL, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("result", result);
                showSuccToast("成功啦");
                historyPost("添加了一个新商品", getUserId());

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
