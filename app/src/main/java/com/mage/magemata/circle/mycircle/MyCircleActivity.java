package com.mage.magemata.circle.mycircle;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;

import org.xutils.view.annotation.ViewInject;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;

/**
 * Created by Administrator on 2017/9/14.
 */

public class MyCircleActivity extends BaseActivity {
    @ViewInject(R.id.mycircle_tab)
    private PageNavigationView tab;
    @ViewInject(R.id.mycircle_viewPager)
    private ViewPager viewPager;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mycircle);
        initToolbar("我的圈子",true);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData() {
        NavigationController navigationController = tab.material()
                .addItem(R.mipmap.tab_circle, "我的圈子",getColor(R.color.rect))
                .addItem(R.mipmap.tab_circle, "我的条目",getColor(R.color.hot_pink))
//                .addItem(R.mipmap.tab_publish, "我的评论",getColor(R.color.green))
                .build();
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(), navigationController.getItemCount());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
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
                    return new MyCircleFragment();
                case 1:
                    return new MyCircleItemFragment();
                default:
                    return new MyCommentFragment();
            }
        }

        @Override
        public int getCount() {
            return size;
        }
    }
}
