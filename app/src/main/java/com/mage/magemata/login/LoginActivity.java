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

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.main.MainActivity;
import com.mage.magemata.user.User;
import com.mage.magemata.user.UserInfoActivity;
import com.mage.magemata.util.MyPrefence;
import com.mage.magemata.util.PublicMethod;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import es.dmoral.toasty.Toasty;
import shem.com.materiallogin.DefaultLoginView;
import shem.com.materiallogin.DefaultRegisterView;
import shem.com.materiallogin.MaterialLoginView;

import static com.mage.magemata.constant.Constant.GET_USERLOGIN;
import static com.mage.magemata.constant.Constant.POST_USERREGISET;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.httpPost;
import static com.mage.magemata.util.PublicMethod.isJsonarray0;

/**
 * Created by Administrator on 2017/9/7.
 */

public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.videoview)
    private VideoView videoView;
    @ViewInject(R.id.login)
    private View mViewNeedOffset;
    private MyPrefence myPrefence;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        PublicMethod.requestPermission(this);
        super.onCreate(savedInstanceState);
        initEvent();
        setStatus(mViewNeedOffset);

    }

    @Override
    public void initData() {

        myPrefence=MyPrefence.getInstance(LoginActivity.this);
        if(  myPrefence.isLogined()){
            showSuccToast("您已经登陆了");
            MainActivity.actionstart(LoginActivity.this);
            finish();
        }
        final MaterialLoginView login = (MaterialLoginView) findViewById(R.id.login);
        ((DefaultLoginView)login.getLoginView()).setListener(new DefaultLoginView.DefaultLoginViewListener() {
            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                if(  iseditEmpty(loginUser.getEditText())||iseditEmpty(loginPass.getEditText())){
                    showErrorToast("输入不能为空");
                }
                else{
                    Map<String, String> userinfo=new HashMap<String, String>();
                    userinfo.put("username",loginUser.getEditText().getText().toString());
                    userinfo.put("password",loginPass.getEditText().getText().toString());
                    httpPost(GET_USERLOGIN, userinfo, new Callback.CommonCallback<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            LOG(result.toString());
                            showSuccToast("登陆成功!");
                            User user=new Gson().fromJson(result.toString(), User.class);
                            myPrefence.saveUser(user);
                            MainActivity.actionstart(LoginActivity.this);
                            finish();
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            LOG(ex.toString());
                            showErrorToast("用户密码输入错误了");
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                }

            }
        });

        ((DefaultRegisterView)login.getRegisterView()).setListener(new DefaultRegisterView.DefaultRegisterViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
                if (iseditEmpty(registerUser.getEditText()) || iseditEmpty(registerPass.getEditText())) {
                    showErrorToast("输入不能为空");
                } else {
                    Map<String, String> userinfo = new HashMap<String, String>();
                    userinfo.put("username", registerUser.getEditText().getText().toString());
                    userinfo.put("password", registerPass.getEditText().getText().toString());
                    httpPost(POST_USERREGISET, userinfo, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            LOG(result);
                            if(result.contains("exist")){
                                showErrorToast("用户名已存在");

                            }
                            else
                                showSuccToast("注册成功快去登录");
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            LOG(ex.toString());
                            showErrorToast("网络出错了");

                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void loadData() {

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
        String url="http://gslb.miaopai.com/stream/AUxY6qmkBRu-4GxzO8uKpF6gd770Stjb.mp4";
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
