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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.mage.magemata.R;

import de.hdodenhof.circleimageview.CircleImageView;

import com.mage.magemata.main.MainActivity;
import com.mage.magemata.publish.PublishFragment;
import  com.vondear.rxtools.RxCameraUtils;
import  com.vondear.rxtools.RxImageUtils;
import com.vondear.rxtools.RxPhotoUtils;

import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.mage.magemata.R.id.activty_navbar;
import static com.mage.magemata.R.id.container;

/**
 * Created by Administrator on 2017/4/25.
 */

public class MoreFragment extends Fragment {
    private Activity activity;
    private CircleImageView imageView;
    private LinearLayout linearLayout;
    private RelativeLayout backgournd;

    @ViewInject(R.id.show_bk)
    private ImageView iv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_more, container, false);
        this.activity = getActivity();
        setListener(mview);

        return mview;
    }


    private  void setListener(View mview){

        imageView = (CircleImageView) mview.findViewById(R.id.more_userview);
       linearLayout=(LinearLayout)mview.findViewById(R.id.more_linel_changeskin);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ChangeSkinActivity.actionStart(activity);
                UserInfoActivity.actionstart(activity);
            }
        });
        mview.findViewById(R.id.more_gpa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPhotoUtils.openLocalImage(MoreFragment.this);


//                GpaActivity.actionStart(activity);
            }
        });
        mview.findViewById(R.id.more_linel_otherinfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                OtherActivity.actionStart(activity);
            }
        });
        mview.findViewById(R.id.more_linel_mywallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                WalletActivity.actionStart(activity);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RxPhotoUtils.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                Log.e("sdf","Sdf");
                if (resultCode == RESULT_OK) {
                    String url=RxPhotoUtils.getRealFilePath(activity,data.getData());

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
        backgournd = (RelativeLayout) activity.findViewById(R.id.main_background);
        Bitmap bitmap = RxImageUtils.getBitmap(url);
        Drawable drawable=RxImageUtils.bitmap2Drawable(bitmap);
        Log.e("sd",url);
        backgournd.setBackground(drawable);
    }
}
