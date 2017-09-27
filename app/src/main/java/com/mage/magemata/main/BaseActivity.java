package com.mage.magemata.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.mage.magemata.R;
import com.mage.magemata.user.User;
import com.mage.magemata.user.UserInfoActivity;
import com.mage.magemata.util.MyPrefence;
import com.mage.magemata.util.MySweetAlertDialog;
import com.mage.magemata.util.PublicMethod;
import com.mage.magemata.util.SkinManager;
import com.vondear.rxtools.RxDataUtils;
import com.vondear.rxtools.RxNetUtils;
import com.vondear.rxtools.RxPhotoUtils;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static com.mage.magemata.constant.Constant.UPLOAD;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.zipImage;
import static com.vondear.rxtools.RxImageUtils.getBitmap;
import static com.vondear.rxtools.RxPhotoUtils.GET_IMAGE_FROM_PHONE;

/**
 * Created by Administrator on 2017/9/7.
 */

public abstract class BaseActivity extends AppCompatActivity {

//            x.view().inject(this);

    @ViewInject(R.id.upload_iv_addimg)
    CircleImageView uploadimg;
    public String uploadimage_URL;
    Toasty mToast;
    private MySweetAlertDialog mDialog;
    protected static int CURRENT_GOOD_ID;
    protected static int CURRENT_CIRCLE_ID;


    //加载背景条
    @Override
    protected void onResume() {
//        SkinManager skinManager = new SkinManager(this);
//        skinManager.getSkin();
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView();
        x.view().inject(this);
        checkInternet();
        initData();
        loadData();
    }

    public void ShowToast(String text) {
        Toasty.success(this, text, Toast.LENGTH_SHORT, true).show();
    }

    public abstract void setContentView();

    public abstract void initData();
    public abstract void loadData();

    protected Bundle setBundle(String key,int value){
        Bundle bundle=new Bundle();
        bundle.putInt(key,value);
        return bundle;
    }

    protected int getBundleid(String key){
        Bundle bundle=new Bundle();
        return bundle.getInt(key);
    }

    //获取用户信息
    protected String getUserId(){
        return MyPrefence.getInstance(this).getUserId();
    }
    protected User getUserInfo(){
        return MyPrefence.getInstance(this).getUser();
    }

    //打开imagepick

    protected void openImageIntent(){
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,GET_IMAGE_FROM_PHONE);
    }

    //打开上传图片
    //需要压缩后的最终图片地址
    public  void upLoadImage(String path,Callback.CommonCallback<String> callback) throws IOException {
        final File file = new File(zipImage(path));
        RequestParams params=new RequestParams(UPLOAD);
        params.setMultipart(true);
        params.addBodyParameter("image", file);//uploadedfile是图片上传的键值名
        params.addBodyParameter("filename",file.getName());
        params.addHeader("Connection", "Keep-Alive");//保持一直连接
        params.addHeader("Charset", "UTF-8");//编码
        params.addHeader("Content-Type", "multipart/form-data");//POST传递过去的编码
        x.http().post(params, callback);
    }

    //添加图片
    @Event(R.id.upload_iv_addimg)
    private void addImage(View view) {
        openImageIntent();
    }
    //图片回馈
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RxPhotoUtils.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
                    final String path = RxPhotoUtils.getRealFilePath(this, data.getData());
                    try {
                        showLoadingDialog(R.string.uploading);
                        upLoadImage(path,new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.e("ssd",result);
                                showSuccToast(R.string.uploadfinish);
                                uploadimage_URL=result;
                                uploadimg.setImageBitmap(getBitmap(path));

                            }
                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Log.e("s",ex.getMessage()+ex.toString());
                                showErrorToast(R.string.uploaderror);
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {
                            }
                            @Override
                            public void onFinished() {
                                cancelLoadingDialog();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case RxPhotoUtils.CROP_IMAGE://普通裁剪后的处理
                Log.e("asd",data.getData()+"");
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //沉浸式
    public void setStatus(View view) {
        StatusBarUtil.setTranslucentForImageView(this, 0, view);
    }


    //初始化Toolbar
    protected Toolbar initToolbar(String title, boolean isDisplayHomeAsUp) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUp);
        }
        return toolbar;
    }


    /**
     * 显示默认加载动画 默认加载文字
     */
    protected SweetAlertDialog getConfirmDialog() {
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(this);
        }

        return mDialog.getConfirmDialog();
    }
    protected void showConfirmDialog(String content,SweetAlertDialog.OnSweetClickListener listener) {
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(this);
        }
        mDialog.showConfirmDialog(content,listener);
        mDialog=null;
    }

    protected void showLoadingDialog() {
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(this);
        }
        mDialog.loading("努力加载中!..");
    }


    /**
     * 显示默认加载动画 自定义加载文字
     *
     * @param str
     */
    protected void showLoadingDialog(String str) {
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(this);
        }
        mDialog.loading(str);
    }

    /**
     * 显示加载动画 自定义加载文字
     *
     * @param strId
     */
    protected void showLoadingDialog(int strId) {
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(this);
        }
        mDialog.loading(getString(strId));
    }


    /**
     * 取消加载动画
     */
    protected void cancelLoadingDialog() {
        if (null != mDialog) {
            mDialog.success();
            mDialog=null;
        }
    }

    /**
     * 显示错误对话框
     *
     * @param listener
     */
    protected void showErrorDialog(SweetAlertDialog.OnSweetClickListener listener){
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(this);
        }
        mDialog.error(null,listener);
        mDialog=null;
    }
    protected void showSuccDialog(String content){
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(this);
        }
        mDialog.showSuccessDialog(content);
        mDialog=null;
    }

    /**
     * 显示错误对话框
     *
     * @param content
     * @param listener
     */
    protected void showErrorDialog(String content,SweetAlertDialog.OnSweetClickListener listener){
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(this);
        }
        mDialog.error(content,listener);
        mDialog=null;
    }

    /**
     * 显示错误对话框
     *
     * @param contentId
     * @param listener
     */
    protected void showErrorDialog(int contentId,SweetAlertDialog.OnSweetClickListener listener){
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(this);
        }
        mDialog.error(getString(contentId),listener);
        mDialog=null;
    }


    //网络请求
    protected boolean checkInternet(){
        if (RxNetUtils.isNetworkAvailable(this)) {
            return true;
        }
        showErrorToast("请先连接网络");
        return false;
    }

    protected boolean iseditEmpty(EditText text) {
        String t = text.getText().toString();
        return RxDataUtils.isNullString(t);
    }



    //Toast显示
    protected void showToast(String string) {
        Toasty.normal(this, string, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int stringId) {
        Toasty.normal(this, getString(stringId), Toast.LENGTH_SHORT).show();
    }



    protected void showInfoToast(String string){
        Toasty.info(this,string,Toast.LENGTH_SHORT ).show();
    }

    protected void showInfoToast(int  stringId){
        Toasty.info(this,getString(stringId),Toast.LENGTH_SHORT ).show();
    }

    protected void showSuccToast(String string){
        Toasty.success(this, string,Toast.LENGTH_SHORT).show();
    }


    protected void showSuccToast(int  stringId){
        Toasty.success(this, getString(stringId),Toast.LENGTH_SHORT).show();
    }

    protected void showErrorToast(String string){
        Toasty.error(this, string,Toast.LENGTH_SHORT).show();
    }

    protected void showErrorToast(int  stringId){
        Toasty.error(this, getString(stringId),Toast.LENGTH_SHORT).show();
    }



    /**
     * 界面跳转
     *
     * @param clazz 目标Activity
     */
    protected void readyGo(Class<?> clazz) {
        readyGo(clazz, null);
    }

    /**
     * 跳转界面，  传参
     *
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转界面并关闭当前界面
     *
     * @param clazz 目标Activity
     */
    protected void readyGoThenKill(Class<?> clazz) {
        readyGoThenKill(clazz, null);
    }

    /**
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        readyGo(clazz, bundle);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();
        }
        return true;
    }

}
