package com.mage.magemata.publish.lostfound;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.main.MainActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import me.majiajie.pagerbottomtabstrip.MaterialMode;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;

/**
 * Created by Administrator on 2017/9/26.
 */

public class LostFoundActivity  extends BaseActivity{
    @ViewInject(R.id.lostfound_tab)
    private PageNavigationView tab;
    @ViewInject(R.id.lostfound_viewPager)
    private ViewPager viewPager;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_lostfound);
        initToolbar("失物招领",true);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData() {
        NavigationController navigationController = tab.material()
                .addItem(R.mipmap.tab_circle, "失物",getColor(R.color.rect))
                .addItem(R.mipmap.tab_publish, "招领",getColor(R.color.green))
                .build();
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(), navigationController.getItemCount());
        viewPager.setAdapter(adapter);
        navigationController.setupWithViewPager(viewPager);

    }

    @Override
    public void loadData() {

    }


    private class MyViewPagerAdapter extends FragmentPagerAdapter {

        private int size;

        private MyViewPagerAdapter(FragmentManager fm, int size) {
            super(fm);
            this.size = size;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return new LostFragment();
//                case 1:
//                    return new FoundFragment();
                default:
                    return new FoundFragment();
            }
        }

        @Override
        public int getCount() {
            return size;
        }
    }
}
