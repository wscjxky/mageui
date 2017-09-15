package com.mage.magemata.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.adapter.Call;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.vondear.rxtools.RxDeviceUtils;
import com.vondear.rxtools.RxFileUtils;
import com.vondear.rxtools.view.RxToast;

import java.io.File;

import cn.pedant.SweetAlert.ProgressHelper;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/9/13.
 */

public class CheckVersionActivity extends BaseActivity {
    boolean update = false;

    @Override
    public void initData() {
//
        String temp = getResources().getString(R.string.newest_apk_down);
        String timeTip = String.format(temp, RxDeviceUtils.getAppVersionName(CheckVersionActivity.this));
        ShowDialog(timeTip,"http://7xss53.com1.z0.glb.clouddn.com/okhttputils/okgo_v2.0.0.apk");
    }

    @Override
    public void loadData() {
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_checkversion);
        StatusBarUtil.setTransparent(this);
    }




        /**
         * 检查是否有新版本，如果有就升级
         */


        private void ShowDialog(String strAppVersionName, final String apk_down_url) {
            final SweetAlertDialog dialog= getConfirmDialog();
            dialog.setContentText(strAppVersionName);
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    getFile(apk_down_url);
                    dialog.cancel();
                }
            });
            dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    showErrorToast("已经取消当前下载");
                    dialog.cancel();
                }
            });
            dialog.show();
        }

        /** 例子
         * 下载APk文件并自动弹出安装
         */
    public void getFile(String url) {
        Log.e("SDf",url);
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("飞速下载中");
        pDialog.setCancelable(true);
        pDialog.show();
        final ProgressHelper helper=pDialog.getProgressHelper();

        OkGo.<File>get(url)//
                .tag(this)//
                .execute(new FileCallback() {
                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        showErrorToast(R.string.checkInternet);
                    }
                    @Override
                    public void onSuccess(Response<File> response) {
                        // file 即为文件数据，文件保存在指定目录
                        showSuccToast("下载成功啦");
                        File file=response.body();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.setDataAndType(Uri.parse("file://" + file.getAbsolutePath()), "application/vnd.android.package-archive");
                        startActivity(i);
                        pDialog.cancel();
                    }  //文件下载时，可以指定下载的文件目录和文件名

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        helper.setInstantProgress(progress.fraction);
                    }
                });
    }



}
