package com.mage.magemata.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mage.magemata.R;
import com.mage.magemata.circle.CircleFragment;
import com.mage.magemata.user.User;
import com.mage.magemata.util.MyPrefence;
import com.mage.magemata.util.MySweetAlertDialog;
import com.vondear.rxtools.RxNetUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import static com.mage.magemata.constant.Constant.UPLOAD;
import static com.mage.magemata.util.PublicMethod.zipImage;
import static com.vondear.rxtools.RxPhotoUtils.GET_IMAGE_FROM_PHONE;

/**
 * Created by Administrator on 2017/9/12.
 */

public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    protected AppCompatActivity mAppCompatActivity;
    protected MySweetAlertDialog mDialog;


    protected View rootView;
    public View view;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAppCompatActivity= (AppCompatActivity) getActivity();
        initData();
        setData();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =x.view().inject(this,inflater,container);
        return  view;
    }


    // 数据
    protected abstract void initData();

    //为View设置数据
    protected abstract void setData();

    //获取用户信息
    protected String getUserId(){
        return MyPrefence.getInstance(mAppCompatActivity).getUserId();
    }
    protected User getUserInfo(){
        return MyPrefence.getInstance(mAppCompatActivity).getUser();
    }



    //初始化toolbar
    public void initToolbar(String title, boolean isDisplayHomeAsUp) {
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUp);
        }
    }



    protected SweetAlertDialog getConfirmDialog() {
        if (null != mDialog) {
            return mDialog.getConfirmDialog();
        }
        return null;
    }

    /**
     * 显示默认加载动画 默认加载文字
     */
    protected void showLoadingDialog() {
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(mAppCompatActivity);
        }
        mDialog.loading(null);
    }


    /**
     * 显示默认加载动画 自定义加载文字
     *
     * @param str
     */
    protected void showLoadingDialog(String str) {
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(mAppCompatActivity);
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
            mDialog = new MySweetAlertDialog(mAppCompatActivity);
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
            mDialog = new MySweetAlertDialog(mAppCompatActivity);
        }
        mDialog.error(null,listener);
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
            mDialog = new MySweetAlertDialog(mAppCompatActivity);
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
            mDialog = new MySweetAlertDialog(mAppCompatActivity);
        }
        mDialog.error(getString(contentId),listener);
        mDialog=null;
    }

    //Toast显示
    protected void showToast(String string) {
        Toasty.normal(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int stringId) {
        Toasty.normal(getActivity(), getString(stringId), Toast.LENGTH_SHORT).show();
    }



    protected void showInfoToast(String string){
        Toasty.info(getActivity(),string,Toast.LENGTH_SHORT ).show();
    }

    protected void showInfoToast(int  stringId){
        Toasty.info(getActivity(),getString(stringId),Toast.LENGTH_SHORT ).show();
    }

    protected void showSuccToast(String string){
        Toasty.success(getActivity(), string,Toast.LENGTH_SHORT).show();
    }


    protected void showSuccToast(int  stringId){
        Toasty.success(getActivity(), getString(stringId),Toast.LENGTH_SHORT).show();
    }

    protected void showErrorToast(String string){
        Toasty.error(getActivity(), string,Toast.LENGTH_SHORT).show();
    }

    protected void showErrorToast(int  stringId){
        Toasty.error(getActivity(), getString(stringId),Toast.LENGTH_SHORT).show();
    }

    protected void showWarnToast(String string){
        Toasty.warning(getActivity(), string,Toast.LENGTH_SHORT).show();
    }

    protected void showWarnToast(int  stringId){
        Toasty.warning(getActivity(), getString(stringId),Toast.LENGTH_SHORT).show();
    }

    //网络请求
    protected boolean checkInternet(){
        if (RxNetUtils.isNetworkAvailable(getActivity())) {
            return true;
        }
        showErrorToast("请先连接网络");
        return false;
    }

    /**
     * startActivity
     *
     * @param clazz 目标Activity
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
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
        getActivity().finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     * @param bundle      数据
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    public  void upLoadImage(String path, Callback.CommonCallback<String> callback) throws IOException {
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
    protected void openImageIntent(){
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GET_IMAGE_FROM_PHONE);
    }

}