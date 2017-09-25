package com.mage.magemata.publish.delivery;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.publish.delivery.rice.RiceActivity;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 */

public class DeliveryActivity  extends BaseActivity {
    @ViewInject(R.id.banner)
    private Banner banner;

    List<Integer> images= new ArrayList<>();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_delivery);
        initToolbar("Mage配送",true);
    }

    @Override
    public void initData() {
        images.add(R.drawable.wp1);
        images.add(R.drawable.wp2);
        images.add(R.drawable.wp3);
        images.add(R.drawable.wp4);
        banner.setImages(images).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Picasso.with(context).load((Integer) path).into(imageView);

            }
        }).start();
    }

    @Override
    public void loadData() {

    }
    @Event(value = R.id.delivery_linel_rice)
    private   void init_rice(View view ) {
        readyGo(RiceActivity.class);
    }
    @Event(value = R.id.delivery_linel_shop)
    private  void init_shop( View view ) {
//        RiceActivity.actionStart(DeliveryActivity.this);
    }
    @Event(value = R.id.delivery_linel_express)
    private  void init_express(View view) {
        readyGo(ExpressActivity.class);
    }


}