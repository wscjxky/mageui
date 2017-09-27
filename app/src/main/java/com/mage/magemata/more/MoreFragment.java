package com.mage.magemata.more;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.mage.magemata.R;


import com.mage.magemata.login.LoginActivity;
import com.mage.magemata.main.BaseFragment;
import com.mage.magemata.user.UserInfoActivity;
import com.mage.magemata.util.MyPrefence;
import  com.vondear.rxtools.RxImageUtils;
import com.vondear.rxtools.RxPhotoUtils;
import com.vondear.rxtools.view.dialog.RxDialog;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


import static android.app.Activity.RESULT_OK;
import static com.mage.magemata.constant.Constant.FLASH_USER_ID;
import static com.mage.magemata.constant.Constant.SET_BACK_GROUND;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.vondear.rxtools.RxPhotoUtils.GET_IMAGE_FROM_PHONE;

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

        Intent intent;
        intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,SET_BACK_GROUND);
    }

    @Event(R.id.more_gpa)
    private void userinfo(View view){
        Bundle bundle=new Bundle();
        bundle.putInt("type",0);
        bundle.putString(FLASH_USER_ID,getUserId());
        readyGo(UserInfoActivity.class,bundle);
    }
    @Event(R.id.more_linel_otherinfo)
    private void otherinfo(View view){
        readyGo(OtherActivity.class);

    }
    @Event(R.id.more_linel_mywallet)
    private void wallet(View view){
        readyGo(WalletActivity.class);
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
                readyGoThenKill(LoginActivity.class);
            }
        });
        settingDialog.setContentView(dialogView1);
        settingDialog.show();
    }
    @Event(R.id.more_linel_gpa)
    private void gpa(View view){
        readyGo(GpaActivity.class);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SET_BACK_GROUND://选择相册之后的处理
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
