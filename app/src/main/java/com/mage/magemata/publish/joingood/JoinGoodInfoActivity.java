package com.mage.magemata.publish.joingood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/5/30.
 */
public class JoinGoodInfoActivity extends BaseActivity {
    private int TOTALPRICE;
    @ViewInject(R.id.joingoodinfo_tv_shopname)
    TextView shopnameet;
    @ViewInject(R.id.joingoodinfo_tv_shopsort)
    TextView shopsortet;
    @ViewInject(R.id.joingoodinfo_tv_totalprice)
    TextView totalpriceet;
    @ViewInject(R.id.joingoodinfo_tv_content)
    TextView contentet;
    @ViewInject(R.id.joingoodinfo_iv_logo)
    CircleImageView logoiv;
    @ViewInject(R.id.joingoodinfo_tv_remainprice)
    TextView remainprice;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_joingoodinfo);
        initToolbar("凑单详情",true);
    }

    @Override
    public void initData() {

    }

    @Override
    public void loadData() {

    }

//    public void onEvent(JoinGood event) {
//        shopnameet.setText(event.getShopname());
//        shopsortet.setText(event.getShopSort());
//        totalpriceet.setText(event.getTotalPrice());
//        contentet.setText(event.getIntroduction());
//        logoiv.setImageBitmap(event.getShopLogo());
//        TOTALPRICE=Integer.valueOf(event.getTotalPrice());
//        remainprice.setText(event.remainPrice());
//    }
//    public void refreshPrice(JoinGoodCustomer event) {
//        remainprice.setText((TOTALPRICE-event.getCurrentPrice()+""));
//    }

    @Event(value = R.id.joingoodinfo_btn_join)
    private void join(View view){
        readyGo(JoinGoodCustomerActivity.class);
    }
}
