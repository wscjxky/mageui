package com.mage.magemata.publish.delivery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;

import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/9/24.
 */

public class ExpressActivity extends BaseActivity {

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_express);
        initToolbar("收取快递",true);

    }

    @Override
    public void initData() {

    }

    @Override
    public void loadData() {

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ExpressActivity.class);
        context.startActivity(intent);
    }
    @Event(R.id.express_btn_submit)
    private void submit(View view){
        final SweetAlertDialog dialog=getConfirmDialog();
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();

                finish();
            }
        })
        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
            }
        })
         .show();

    }
}
