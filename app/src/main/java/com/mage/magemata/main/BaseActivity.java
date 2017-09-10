package com.mage.magemata.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.mage.magemata.user.UserInfoActivity;
import com.mage.magemata.util.PublicMethod;
import com.mage.magemata.util.SkinManager;
import com.yanzhenjie.permission.AndPermission;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/9/7.
 */

public abstract class BaseActivity extends AppCompatActivity {
//    @ViewInject(R.id.login_et_username)
//    private EditText et_username;
//            x.view().inject(this);



    Toasty mToast;

    @Override
    protected void onResume(){
        SkinManager skinManager=new SkinManager(this);

        skinManager.getSkin();
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView();
        x.view().inject(this);

        initData();
    }
    public void ShowToast(String text) {
        Toasty.success(this, text, Toast.LENGTH_SHORT, true).show();

    }
    public abstract void initData();

    public abstract void setContentView();
    public  void setStatus(View view){
        StatusBarUtil.setTranslucentForImageView(this, 0, view);
    }

//    public static void actionstart(Context context){
//        Intent intent=new Intent(context,MainActivity.class);
//        context.startActivity(intent);
//    }
}
