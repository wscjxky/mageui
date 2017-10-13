package com.mage.magemata.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RelativeLayout;

import com.mage.magemata.R;
import com.mage.magemata.chat.ChatFragment;
import com.mage.magemata.circle.CircleFragment;
import com.mage.magemata.publish.PublishFragment;
import com.mage.magemata.more.MoreFragment;
import com.mage.magemata.util.PublicMethod;
import com.vondear.rxtools.RxImageUtils;

import org.xutils.view.annotation.ViewInject;

import java.io.FileNotFoundException;
import java.util.Objects;

import me.majiajie.pagerbottomtabstrip.MaterialMode;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class MainActivity extends BaseActivity {
    @ViewInject(R.id.activty_navbar)
    private PageNavigationView tab;
    @ViewInject(R.id.activty_viewpager)
    private ViewPager pager;
    private MyViewPagerAdapter adapter;
    @ViewInject(R.id.main_background)
    RelativeLayout background;
    private NavigationController navigationController;

    //刷新界面
    @Override
    protected void onResume() {
        super.onResume();
        getSkin();

    }
    //6.0以下的必须用
//    getResources().getColor(R.color.darkkhaki)
    @Override
    public void initData() {
        PublicMethod.requestPermission(this);
//        PageNavigationView tab = (PageNavigationView) findViewById(R.id.activty_navbar);
         navigationController = tab.material()
                .addItem(R.mipmap.tab_circle, "圈子",getResources().getColor(R.color.darkkhaki))
                .addItem(R.mipmap.tab_publish, "发布",getResources().getColor(R.color.green))
                .addItem(android.R.drawable.stat_notify_chat, "私聊",getResources().getColor(R.color.hot_pink))
                .addItem(R.mipmap.ico_mychat_selected, "我的",getResources().getColor(R.color.darkblue))
                .setDefaultColor(getResources().getColor(R.color.yellow))//未选中状态的颜色
                .setMode(MaterialMode.HIDE_TEXT )//这里可以设置样式模式，总共可以组合出4种效果
                .build();
        navigationController.setHasMessage(0,true);
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                Log.i("asd","selected: " + index + " old: " + old);
            }

            @Override
            public void onRepeat(int index) {
                navigationController.setHasMessage(index,false);
                Log.i("asd","onRepeat selected: " + index);
            }
        });
        adapter=new MyViewPagerAdapter(getSupportFragmentManager(),navigationController.getItemCount());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
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

        private MyViewPagerAdapter(FragmentManager fm,int size) {
            super(fm);
            this.size = size;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 1:
                    return new PublishFragment();
                case 2:
                    return new ChatFragment();
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
        SharedPreferences skinSettingPreference = MainActivity.this.getSharedPreferences("background", Context.MODE_PRIVATE);
        String back = skinSettingPreference.getString("background", "");
        if (!Objects.equals(back, "")&&!back.contains("resource")) {
            Drawable drawable =RxImageUtils.bitmap2Drawable(RxImageUtils.getBitmap(back)) ;
            background.setBackground(drawable);
        }
        else if(!Objects.equals(back, "")){
            Drawable d= null;
            try {
                d = Drawable.createFromStream(getContentResolver().openInputStream(Uri.parse(back)),null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            background.setBackground(d);
        }
    }
}
