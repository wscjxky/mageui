package com.mage.magemata.user;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.mage.magemata.R;
import com.mage.magemata.circle.SlidingUpBaseActivity;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.main.MainActivity;
import com.sackcentury.shinebuttonlib.ShineButton;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/9/8.
 */

public class UserInfoActivity extends BaseActivity  implements AppBarLayout.OnOffsetChangedListener {
    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;
    private int mMaxScrollSize;
    private boolean followed=false;
    @ViewInject(R.id.userinfo_ll_toolbar)
    LinearLayout toorlbar;
    @ViewInject(R.id.userinfo_but_follow)
    private ShineButton follow_btn;

    @ViewInject(R.id.userinfo_tl_tabs)
    TabLayout tabLayout;
    @ViewInject(R.id.userinfo_vp_viewpager)
    ViewPager viewPager;
    @ViewInject(R.id.userinfo_al_appbar)
    AppBarLayout appbarLayout;
    @ViewInject(R.id.user_cv_userimage)
    CircleImageView mProfileImage;
    public String[] Title={"关注","粉丝","足迹"};

    @Override
    public void initData() {
        follow_btn.init(this);
        follow_btn.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                Toasty.success(UserInfoActivity.this,"关注成功").show();

            }
        });
        StatusBarUtil.setTranslucentForCoordinatorLayout (UserInfoActivity.this,0);
        appbarLayout.addOnOffsetChangedListener(UserInfoActivity.this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();
        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(),Title));
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_userinfo);
        x.view().inject(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;

            mProfileImage.animate()
                    .scaleY(0).scaleX(0)
                    .setDuration(200)
                    .start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }

    public static void actionstart(Context context ){
        Intent intent=new Intent(context,UserInfoActivity.class);
        context.startActivity(intent);
    }
    private static class TabsAdapter extends FragmentPagerAdapter {
        private  static final int TAB_COUNT = 3;
        private String[] mTitle;
        TabsAdapter(FragmentManager fm , String[] mTitle) {
            super(fm);
            this.mTitle=mTitle;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public Fragment getItem(int i) {
            return UserPageFragment.newInstance();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }
    }

}
