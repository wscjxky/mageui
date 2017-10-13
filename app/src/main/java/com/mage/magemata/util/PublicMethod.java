package com.mage.magemata.util;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Path;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.mage.magemata.R;
import com.mage.magemata.chat.ChatActivity;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.main.MainActivity;
import com.mage.magemata.user.UserInfoActivity;
import com.vondear.rxtools.RxImageUtils;
import com.yanzhenjie.permission.AndPermission;

import org.json.JSONArray;
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

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.mage.magemata.App.APP_DIR;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.constant.Constant.UPLOAD;
import static com.vondear.rxtools.RxImageUtils.compressByQuality;
import static com.vondear.rxtools.RxImageUtils.getBitmap;
import static com.vondear.rxtools.RxImageUtils.save;
import static com.vondear.rxtools.RxTimeUtils.getCurrentDateTime;
import static com.vondear.rxtools.RxTimeUtils.getDate;
import static com.vondear.rxtools.RxZipUtils.zipFile;
import static org.xutils.http.HttpMethod.DELETE;

/**
 * Created by Administrator on 2017/9/7.
 */

public  class PublicMethod {
    private static final int ZIP_IMAGE_QUALITY = 50 ;
//    压缩图片
//    返回压缩后图片的地址
    public static String zipImage(String path){
        File file = new File(path);
        Bitmap bitmap=compressByQuality(getBitmap(file),ZIP_IMAGE_QUALITY);
        String zippath=APP_DIR+getCurrentDateTime("ddHHmmss")+".jpg";
        if(RxImageUtils.save(bitmap,zippath, Bitmap.CompressFormat.JPEG,true))
            return  zippath;
        return path;
    }

//    获取权限
    public static void requestPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
    }
    //    新建map
    public static Map<String,String> getMap(){
        return new HashMap<String,String>();
    }
    //    判断网络请求返回的json类型
    //    返回1-jsonobject
    //    0-jsonarray
    //    2-string
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
    // post请求，传入map参数和callback
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
    // get请求
    public static void httpGet(String url, Callback.CommonCallback<?> callback) {
        Log.e("url",url);
        RequestParams params = new RequestParams(url);
        x.http().get(params,callback);
    }
    //del请求
    public static void httpDel(String url  ,Callback.CommonCallback<?> callback) {
        Log.e("url",url);
        RequestParams params = new RequestParams(url);
        x.http().request(DELETE,params,callback);
    }
    public static void LOG(String s){
        Log.e("测试", s);
    }

    // 上传历史信息
    public static void historyPost(String content ,String user_id) {
        String post_History=ROOT_URL+"userhistory";
        RequestParams params = new RequestParams(post_History);
        params.addQueryStringParameter("content", content);
        params.addQueryStringParameter("user_id", user_id);
        x.http().post(params, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                LOG("路径记录");
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    public static void showNotice(Context context){
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentText("主内容区");
        builder.setContentTitle("通知标题");
        builder.setSmallIcon(R.mipmap.logo2);
        builder.setTicker("MageMata新消息");
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());//设置时间，设置为系统当前的时间
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        /**
         * vibrate属性是一个长整型的数组，用于设置手机静止和振动的时长，以毫秒为单位。
         * 参数中下标为0的值表示手机静止的时长，下标为1的值表示手机振动的时长， 下标为2的值又表示手机静止的时长，以此类推。
         */
        long[] vibrates = { 0, 1000, 1000, 1000 };
        notification.vibrate = vibrates;

        /**
         * 手机处于锁屏状态时， LED灯就会不停地闪烁， 提醒用户去查看手机,下面是绿色的灯光一 闪一闪的效果
         */
        notification.ledARGB = Color.GREEN;// 控制 LED 灯的颜色，一般有红绿蓝三种颜色可选
        notification.ledOnMS = 1000;// 指定 LED 灯亮起的时长，以毫秒为单位
        notification.ledOffMS = 1000;// 指定 LED 灯暗去的时长，也是以毫秒为单位
        notification.flags = Notification.FLAG_SHOW_LIGHTS;// 指定通知的一些行为，其中就包括显示
        // LED 灯这一选项

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        notification.sound = uri;
        notification.defaults = Notification.DEFAULT_ALL;

        manager.notify(1, notification);
    }
}
