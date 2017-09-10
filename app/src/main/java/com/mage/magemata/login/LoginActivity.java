package com.mage.magemata.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.jaeger.library.StatusBarUtil;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.main.MainActivity;
import com.mage.magemata.user.UserInfoActivity;
import com.mage.magemata.util.PublicMethod;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import shem.com.materiallogin.DefaultLoginView;
import shem.com.materiallogin.DefaultRegisterView;
import shem.com.materiallogin.MaterialLoginView;

/**
 * Created by Administrator on 2017/9/7.
 */

public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.videoview)
    private VideoView videoView;
    @ViewInject(R.id.login)
    private View mViewNeedOffset;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        initEvent();
        setStatus(mViewNeedOffset);
    }

    @Override
    public void initData() {
        PublicMethod.requestPermission(this);
        final MaterialLoginView login = (MaterialLoginView) findViewById(R.id.login);
        ((DefaultLoginView)login.getLoginView()).setListener(new DefaultLoginView.DefaultLoginViewListener() {
            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                MainActivity.actionstart(LoginActivity.this);
                finish();
            }
        });

        ((DefaultRegisterView)login.getRegisterView()).setListener(new DefaultRegisterView.DefaultRegisterViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
                //Handle register
            }
        });
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
    }


    public static void actionstart(Context context ){
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initEvent() {
        String path= Environment.getExternalStorageDirectory().getPath() + "/test.mp4";
        String url="http://gslb.miaopai.com/stream/Q8Rea0w4QCO8uqh3ZufQhLxyCh8T0w~b5EjYvw__.mp4";
        File file=new File(path);
        final Uri uri = Uri.parse(url);
        if (!file.exists()) {
            Toast.makeText(this, "视频文件路径错误", Toast.LENGTH_SHORT).show();

        }else {
            final android.widget.MediaController mp=new android.widget.MediaController(this);
            mp.setVisibility(View.INVISIBLE);
            videoView.setMediaController(mp);
            videoView.setClickable(false);
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    videoView.setVideoURI(uri);
                    videoView.start();
                }
            });
            videoView.setVideoURI(uri);
            videoView.start();
        }
    }
}
