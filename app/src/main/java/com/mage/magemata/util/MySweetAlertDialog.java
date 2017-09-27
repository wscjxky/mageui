package com.mage.magemata.util;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;

import com.mage.magemata.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/9/12.
 */

public class MySweetAlertDialog {
    private SweetAlertDialog mSweetAlertDialog;
    private Activity mActivity;
    private static final String TAG = "MySweetAlertDialog";

    public MySweetAlertDialog(Activity activity){
        Log.d(TAG, "MySweetAlertDialog: ");
        mSweetAlertDialog=new SweetAlertDialog(activity);
        mActivity=activity;
    }

    public void loading(String title){
        if (mSweetAlertDialog != null) {
            Log.d(TAG, "loading: ");
            mSweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
            mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            mSweetAlertDialog.setTitleText(title==null?mActivity.getString(R.string.loading):title)
                    .setCanceledOnTouchOutside(false);
            mSweetAlertDialog.setCancelable(false);
            mSweetAlertDialog.show();
        }
    }

    public void success(){
        if (mSweetAlertDialog != null&&mSweetAlertDialog.isShowing()) {
            Log.d(TAG, "success: ");
            mSweetAlertDialog.dismissWithAnimation();
        }
    }
    public SweetAlertDialog showSuccessDialog(String content) {
        if (mSweetAlertDialog != null) {
            mSweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            mSweetAlertDialog.setConfirmText("确定");
            mSweetAlertDialog.setTitleText(content==null?"成功":content);
            mSweetAlertDialog.setCanceledOnTouchOutside(true);
            mSweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    mSweetAlertDialog.dismiss();
                }
            });

            if (!mSweetAlertDialog.isShowing()) {
                mSweetAlertDialog.show();
            }
        }
        return null;
    }

    public SweetAlertDialog getConfirmDialog() {
        if (mSweetAlertDialog != null) {
            mSweetAlertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
            mSweetAlertDialog.setTitleText("确定吗");
            mSweetAlertDialog.setCancelText("我再想想");
            mSweetAlertDialog.setConfirmText("确定了!");
            mSweetAlertDialog.showCancelButton(true);
            mSweetAlertDialog.setCancelable(false);
            mSweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    mSweetAlertDialog.dismiss();
                }
            });

            return mSweetAlertDialog;
        }
        return null;
    }
    public void showConfirmDialog(String content,SweetAlertDialog.OnSweetClickListener listener) {
        if (mSweetAlertDialog != null) {
            mSweetAlertDialog
                    .setTitleText(content)
                    .setCancelText("我再想想")
                    .setConfirmText("确定了!")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    })
                    .setConfirmClickListener(listener == null ? new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    } : listener)
                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
            mSweetAlertDialog.setCanceledOnTouchOutside(false);

            if (!mSweetAlertDialog.isShowing()) {
                mSweetAlertDialog.show();
            }
        }
    }


    public void error(String content, SweetAlertDialog.OnSweetClickListener listener){
        if (mSweetAlertDialog != null) {
            mSweetAlertDialog.setTitleText(mActivity.getString(R.string.Error))
                    .setContentText(content==null?mActivity.getString(R.string.check):content)
                    .setConfirmText(mActivity.getString(R.string.back))
                    .setConfirmClickListener(listener==null?new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            if (mActivity != null) {
                                mActivity.finish();
                            }
                        }
                    }:listener)
                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
            mSweetAlertDialog.setCanceledOnTouchOutside(false);
            //如果没在显示则直接显示错误对话框
            if (!mSweetAlertDialog.isShowing()) {
                mSweetAlertDialog.show();
            }
        }
    }

    public SweetAlertDialog getSweetAlertDialog(){
        return mSweetAlertDialog;
    }

}