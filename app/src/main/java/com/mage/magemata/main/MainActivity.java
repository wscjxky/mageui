package com.mage.magemata.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.color.CircleView;
import com.jaeger.library.StatusBarUtil;
import com.mage.magemata.R;
import com.mage.magemata.circle.CircleFragment;
import com.mage.magemata.publish.PublishFragment;
import com.mage.magemata.user.MoreFragment;
import com.mage.magemata.util.MyPrefence;
import com.mage.magemata.util.PublicMethod;
import com.vondear.rxtools.RxImageUtils;
import com.vondear.rxtools.RxPhotoUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Objects;

import me.majiajie.pagerbottomtabstrip.MaterialMode;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;

import static com.mage.magemata.util.PublicMethod.requestPermission;

public class MainActivity extends BaseActivity {
    @ViewInject(R.id.activty_navbar)
    private PageNavigationView tab;
    @ViewInject(R.id.activty_viewpager)
    private ViewPager pager;
    private MyViewPagerAdapter adapter;
    @ViewInject(R.id.main_background)
    RelativeLayout background;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData() {
        PublicMethod.requestPermission(this);
        getSkin();

//        PageNavigationView tab = (PageNavigationView) findViewById(R.id.activty_navbar);

        NavigationController navigationController = tab.material()
                .addItem(R.mipmap.tab_circle, "圈子",getColor(R.color.darkkhaki))
                .addItem(R.mipmap.tab_publish, "发布",getColor(R.color.green))
                .addItem(android.R.drawable.stat_notify_chat, "私聊",getColor(R.color.hot_pink))
                .addItem(R.mipmap.ico_mychat_selected, "我的",getColor(R.color.darkblue))
                .setDefaultColor(getColor(R.color.yellow))//未选中状态的颜色
                .setMode(  MaterialMode.HIDE_TEXT )//这里可以设置样式模式，总共可以组合出4种效果
                .build();
        adapter=new MyViewPagerAdapter(getSupportFragmentManager(),navigationController.getItemCount());
        pager.setAdapter(adapter);
        navigationController.setupWithViewPager(pager);

    }

    @Override
    public void loadData() {

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
        setStatus(tab);


    }



    public static void actionstart(Context context ){
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        private int size;

        public MyViewPagerAdapter(FragmentManager fm,int size) {
            super(fm);
            this.size = size;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 1:

                    return new PublishFragment();

                case 3:
                    return new MoreFragment();

                default:
                    return new CircleFragment();
            }
        }

        @Override
        public int getCount() {
            return size;
        }
    }

    public void getSkin() {
        SharedPreferences skinSettingPreference = this.getSharedPreferences("background", Context.MODE_PRIVATE);
        String back = skinSettingPreference.getString("background", "");
        Log.e("ASd",back);
        if (!Objects.equals(back, "")) {
            Drawable drawable =RxImageUtils.bitmap2Drawable(RxImageUtils.getBitmap(back)) ;
            background.setBackground(drawable);
        }
    }
}
