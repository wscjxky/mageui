package com.mage.magemata.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.mage.magemata.R;
import com.mage.magemata.circle.Circle;
import com.mage.magemata.circle.SlidingUpBaseActivity;
import com.mage.magemata.circle.card.Circle_Item_Activity;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.main.MainActivity;
import com.mage.magemata.more.MoreFragment;
import com.mage.magemata.util.MyPrefence;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;
import com.vondear.rxtools.RxDataUtils;
import com.vondear.rxtools.RxImageUtils;
import com.vondear.rxtools.RxPhotoUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static com.mage.magemata.constant.Constant.FLASH_USER_ID;
import static com.mage.magemata.constant.Constant.FOLLOW_USER_ID;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.constant.Constant.SET_BACK_GROUND;
import static com.mage.magemata.constant.Constant.USER;
import static com.mage.magemata.constant.Constant.USER_FOLLOW;
import static com.mage.magemata.constant.Constant.USER_ID;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.getMap;
import static com.mage.magemata.util.PublicMethod.httpGet;
import static com.mage.magemata.util.PublicMethod.httpPost;
import static com.vondear.rxtools.RxPhotoUtils.GET_IMAGE_FROM_PHONE;

/**
 * Created by Administrator on 2017/9/8.
 */

public class UserInfoActivity extends BaseActivity  implements AppBarLayout.OnOffsetChangedListener {
    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;
    private int mMaxScrollSize;
    private String get_user_url=ROOT_URL+"user/";
    private String user_follow_url=ROOT_URL+"userfollow/";

    @ViewInject(R.id.userinfo_btn_chat)
    FloatingActionButton btn_chat;
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
    @ViewInject(R.id.userinfo_ct_colltoolbar)
    CollapsingToolbarLayout userbackground;
    private int type;  //type为0则是用户本信息
    public String[] Title={"关注","粉丝","足迹"};
    //跳转变化的userid
    public  static String flash_user_id;
    private String USER_BACKGROUND = "user_background";
    @Override
    public void initData() {
        getBack();


        if(type==0){
            follow_btn.setVisibility(View.GONE);
            btn_chat.setVisibility(View.GONE);
            flash_user_id= getUserId();
        }
        else{
            mProfileImage.setClickable(false);
        }

        follow_btn.init(this);
        follow_btn.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, final boolean checked) {
                Map<String,String> map=getMap();
                map.put(USER_ID,getUserId()+"");
                map.put(FOLLOW_USER_ID,flash_user_id+"");
                httpPost(user_follow_url, map, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if(Objects.equals(result, "ok")) {
                            showErrorToast(R.string.subscribe_success);
                            follow_btn.setChecked(true);
                        }
                        else {
                            showErrorToast(R.string.subscribe_failed);
                            follow_btn.setChecked(false);
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                            showErrorToast(R.string.checkInternet);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
        StatusBarUtil.setTranslucentForCoordinatorLayout (UserInfoActivity.this,0);
        appbarLayout.addOnOffsetChangedListener(UserInfoActivity.this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();
        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(),Title));
        viewPager.setOffscreenPageLimit(2);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void loadData() {
        httpGet(get_user_url+flash_user_id, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                User user=new Gson().fromJson(result.toString(),User.class);
                if(!RxDataUtils.isNullString(user.getProfile())) {
                    Picasso.with(UserInfoActivity.this)
                            .load(user.getProfile())
                            .resize(200, 200)
                            .centerCrop()
                            .into(mProfileImage);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LOG(ex.toString());
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
        setContentView(R.layout.activity_userinfo);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type");//读出数据
        //跳转回到自己的主页
        flash_user_id=bundle.getString(FLASH_USER_ID);
        if(Objects.equals(flash_user_id, getUserId())){
            type=0;
        }
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
    @Event(R.id.user_cv_userimage)
    private void changeinfo(View view){
            readyGo(ChangeUserActivity.class);
    }

    public static void actionstart(Context context , Bundle bundle){

        Intent intent=new Intent(context,UserInfoActivity.class);
        intent.putExtras(bundle);
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
            return UserPageFragment.newInstance(i);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }
    }

    @Event(R.id.userinfo_ct_colltoolbar)
    private void changeBackground(View view){
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,SET_BACK_GROUND);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SET_BACK_GROUND://选择相册之后的处理
                if (resultCode == RESULT_OK) {
                    String url=RxPhotoUtils.getRealFilePath(UserInfoActivity.this,data.getData());
                    setBack(url);
                }
                break;
            case RxPhotoUtils.CROP_IMAGE://普通裁剪后的处理
                Log.e("asd",data.getData()+"");
//               RequestUpdateAvatar(new File(RxPhotoUtils.getRealFilePath(activity, RxPhotoUtils.cropImageUri)));
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void setBack(String path){
        MyPrefence myPrefence=MyPrefence.getInstance(this);
        myPrefence.saveString(USER_BACKGROUND,path);
        Bitmap bitmap = RxImageUtils.getBitmap(path);
        Drawable drawable=RxImageUtils.bitmap2Drawable(bitmap);
        userbackground.setBackground(drawable);
    }
    public void getBack(){
        MyPrefence myPrefence=MyPrefence.getInstance(this);
        String path=myPrefence.getString(USER_BACKGROUND);

        if(path!=null) {
            LOG(path);
            Bitmap bitmap = RxImageUtils.getBitmap(path);
            Drawable drawable = RxImageUtils.bitmap2Drawable(bitmap);
            userbackground.setBackground(drawable);
        }
    }

}
