package com.mage.magemata.publish.usedgood;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mage.magemata.constant.Constant.GOOD_ID;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.httpGet;

/**
 * Created by Administrator on 2017/9/10.
 */

public class GoodInfoActivity  extends BaseActivity{
    private  String GET_URL=ROOT_URL+"good/";
    @ViewInject(R.id.offview)
    private View view;
    @ViewInject(R.id.usedgood_iv_logo)
    private ImageView logo;
    @ViewInject(R.id.usedgood_tv_content)
    private TextView content;
    @ViewInject(R.id.usedgood_tv_ifbargain)
    private TextView bargain;
    @ViewInject(R.id.usedgood_tv_phone)
    private TextView phone;
    @ViewInject(R.id.usedgood_tv_price)
    private TextView price;
    @ViewInject(R.id.usedgood_tv_name)
    private TextView title;

    @Override
    public void initData() {

    }

    @Override
    public void loadData() {
        httpGet(GET_URL+getUserId(), new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    String state=result.getString("state");
                    switch (state){
                        case "ok":

                            List<Good> goods = new Gson().fromJson(result.getString("data"),new TypeToken<ArrayList<Good>>() {}.getType());
                            Good good=goods.get(0);
                            title.setText(good.getGood_name());
                            content.setText(good.getContent());
                            price.setText(good.getPrice());
                            phone.setText(good.getPhone());
                            bargain.setText(good.getBargin());
                            Picasso.with(GoodInfoActivity.this)
                                    .load(good.getImage())
                                    .fit()
                                    .into(logo);
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
    public void setContentView() {
        setContentView(R.layout.activity_goodinfo);
        setStatus(view);
    }

    public static void actionstart(Context context){
        Intent intent=new Intent(context,GoodInfoActivity.class);
        context.startActivity(intent);
    }
}
