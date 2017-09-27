package com.mage.magemata.publish.print;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.vondear.rxtools.RxFileUtils;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.getMap;
import static com.mage.magemata.util.PublicMethod.httpPost;
import static com.vondear.rxtools.RxFileUtils.getDataColumn;
import static com.vondear.rxtools.RxFileUtils.getFileIntent;
import static com.vondear.rxtools.RxFileUtils.isDownloadsDocument;
import static com.vondear.rxtools.RxFileUtils.isExternalStorageDocument;
import static com.vondear.rxtools.RxFileUtils.isGooglePhotosUri;
import static com.vondear.rxtools.RxFileUtils.isMediaDocument;
import static com.vondear.rxtools.RxPhotoUtils.getRealFilePath;

/**
 * Created by Administrator on 2017/5/14.
 */
public class PrintActivity extends BaseActivity {
    private final int FILE_PICK = 100;
    @ViewInject(R.id.print_tv_filename)
    private TextView filename;
    private String uploadfile_url = ROOT_URL + "upload/file";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_print);
        initToolbar("打印", true);
    }

    @Event(R.id.print_btn_submit)
    private void submit(View view) {
        showConfirmDialog(getString(R.string.confirm_submit), new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                upload();
                sweetAlertDialog.dismissWithAnimation();
                showSuccToast(getString(R.string.success_submit));
            }
        });

//        for(int i=0;i<2;i++){
//            request.params("file_"+i,new File(path));
//        }

    }

    @Event(R.id.print_ll_addfile)
    private void addfile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, FILE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_PICK) {
            Uri uri = data.getData();
            if(getPath(this,uri)!=null) {
                filename.setText(getPath(this, uri));
            }
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void loadData() {

    }

    private void upload() {
        Map<String, File> map = new HashMap<String, File>();
        String path = filename.getText().toString();
        File file = new File(path);
        RequestParams params = new RequestParams(uploadfile_url);
        params.setMultipart(true);
        params.addBodyParameter("file", file);//uploadedfile是图片上传的键值名
        params.addBodyParameter("filename", file.getName());
        params.addHeader("Connection", "Keep-Alive");//保持一直连接
        params.addHeader("Charset", "UTF-8");//编码
        params.addHeader("Content-Type", "multipart/form-data");
        //POST传递过去的编码
        x.http().post(params, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LOG(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LOG(ex.toString());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                finish();

            }
        });
    }
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // LocalStorageProvider
            // ExternalStorageProvider
             if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }



}

