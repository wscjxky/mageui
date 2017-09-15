package com.mage.magemata.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.mage.magemata.R;

import de.hdodenhof.circleimageview.CircleImageView;

import com.mage.magemata.login.LoginActivity;
import com.mage.magemata.main.BaseFragment;
import com.mage.magemata.main.MainActivity;
import com.mage.magemata.publish.PublishFragment;
import com.mage.magemata.util.CheckVersionActivity;
import com.mage.magemata.util.MyPrefence;
import  com.vondear.rxtools.RxCameraUtils;
import  com.vondear.rxtools.RxImageUtils;
import com.vondear.rxtools.RxPhotoUtils;
import com.vondear.rxtools.view.dialog.RxDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.mage.magemata.R.id.activty_navbar;
import static com.mage.magemata.R.id.container;
import static com.mage.magemata.util.PublicMethod.LOG;

/**
 * Created by Administrator on 2017/4/25.
 */
@ContentView(R.layout.fragment_more)
public class MoreFragment extends BaseFragment {
    @ViewInject(R.id.more_linel_changeskin)
    LinearLayout linearLayout;
    @ViewInject(R.id.show_bk)
    private ImageView iv;


    @Override
    protected void initData() {
    }
    @Override
    protected void setData() {

    }

    @Event(R.id.more_linel_changeskin)
    private void changskin(View view){
            openImageIntent();
    }

    @Event(R.id.more_gpa)
    private void userinfo(View view){
        Bundle bundle=new Bundle();
        bundle.putInt("type",0);
        readyGo(UserInfoActivity.class,bundle);
    }
    @Event(R.id.more_linel_otherinfo)
    private void otherinfo(View view){

    }
    @Event(R.id.more_linel_mywallet)
    private void wallet(View view){

    }
    @Event(R.id.more_linel_setting)
    private void setting(View view){
        LOG("asd");
        RxDialog settingDialog = new RxDialog(mAppCompatActivity);
        settingDialog.getLayoutParams().gravity = Gravity.BOTTOM;
        View dialogView1 = LayoutInflater.from(mAppCompatActivity).inflate(
                R.layout.dialog_picker_pictrue, null);
        TextView tv_camera = (TextView) dialogView1
                .findViewById(R.id.tv_camera);
        TextView tv_aboutus = (TextView) dialogView1
                .findViewById(R.id.tv_file);
        TextView tv_cancelid = (TextView) dialogView1
                .findViewById(R.id.tv_cancel);
        tv_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }
        });
        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                readyGo(CheckVersionActivity.class);

            }
        });
        tv_cancelid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MyPrefence.getInstance(mAppCompatActivity).logOut();
                LOG(                MyPrefence.getInstance(mAppCompatActivity).isLogined()+""
);
                LoginActivity.actionstart(mAppCompatActivity);
            }
        });
        settingDialog.setContentView(dialogView1);
        settingDialog.show();
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RxPhotoUtils.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                Log.e("sdf","Sdf");
                if (resultCode == RESULT_OK) {
                    String url=RxPhotoUtils.getRealFilePath(mAppCompatActivity,data.getData());
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

    public void setBack(String url){
        SharedPreferences skinSettingPreference=MoreFragment.this.getActivity().getSharedPreferences("background", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = skinSettingPreference.edit();
        editor.putString("background", url);
        editor.apply();
        Bitmap bitmap = RxImageUtils.getBitmap(url);
        Drawable drawable=RxImageUtils.bitmap2Drawable(bitmap);
        Log.e("sd",url);
        RelativeLayout backgournd=(RelativeLayout)getActivity().findViewById(R.id.main_background) ;
        backgournd.setBackground(drawable);
    }
}
