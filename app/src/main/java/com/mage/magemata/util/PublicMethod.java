package com.mage.magemata.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.user.UserInfoActivity;
import com.vondear.rxtools.RxImageUtils;
import com.yanzhenjie.permission.AndPermission;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import es.dmoral.toasty.Toasty;

import static com.mage.magemata.App.APP_DIR;
import static com.mage.magemata.constant.Constant.UPLOAD;
import static com.vondear.rxtools.RxImageUtils.compressByQuality;
import static com.vondear.rxtools.RxImageUtils.getBitmap;
import static com.vondear.rxtools.RxImageUtils.save;
import static com.vondear.rxtools.RxTimeUtils.getCurrentDateTime;
import static com.vondear.rxtools.RxTimeUtils.getDate;
import static com.vondear.rxtools.RxZipUtils.zipFile;

/**
 * Created by Administrator on 2017/9/7.
 */

public  class PublicMethod {
    private static final int ZIP_IMAGE_QUALITY = 50 ;

    public static void requestPermission(Activity activity) {
        if (!AndPermission.hasPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            AndPermission.with(activity)
                    .requestCode(100)
                    .permission(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                            Manifest.permission.MANAGE_DOCUMENTS,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.INTERNET)
                    .send();
        }
    }

    public static void httpPost(String url, Map<String, String> map , Callback.CommonCallback<?> callback) {
        Log.e("url",url);
        RequestParams params = new RequestParams(url);
        Set<String> keyset = map.keySet();
        for(String key : keyset){
            String value = map.get(key);
            params.addQueryStringParameter(key, value);
        }
        x.http().post(params,callback);
    }
    public static void httpGet(String url, Callback.CommonCallback<?> callback) {
        Log.e("url",url);
        RequestParams params = new RequestParams(url);
        x.http().get(params,callback);
    }
    public static int isJsonarray0(String s) {
        Gson gson = new Gson();
        //创建一个JsonParser
        JsonParser parser = new JsonParser();
        JsonElement el = parser.parse(s);
        if (el.isJsonObject()) {
            return 1;
        } else if (el.isJsonArray()) {
            return 0;

        } else {
                return 2;
        }
    }
    public static String zipImage(String path){
        File file = new File(path);
        Bitmap bitmap=compressByQuality(getBitmap(file),ZIP_IMAGE_QUALITY);
        String zippath=APP_DIR+getCurrentDateTime("ddHHmmss")+".jpg";
        if(RxImageUtils.save(bitmap,zippath, Bitmap.CompressFormat.JPEG,true))
            return  zippath;
        return path;
    }
    public static void LOG(String s){
        Log.e("测试", s);
    }

    public static Map<String,String> getMap(){
        return new HashMap<String,String>();
    }
}
