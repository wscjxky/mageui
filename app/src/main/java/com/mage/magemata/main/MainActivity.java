package com.mage.magemata.main;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RelativeLayout;

import com.mage.magemata.R;
import com.mage.magemata.chat.ChatFragment;
import com.mage.magemata.circle.CircleFragment;
import com.mage.magemata.publish.PublishFragment;
import com.mage.magemata.more.MoreFragment;
import com.mage.magemata.util.MyPrefence;
import com.mage.magemata.util.PublicMethod;
import com.vondear.rxtools.RxImageUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import me.majiajie.pagerbottomtabstrip.MaterialMode;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

import static com.mage.magemata.constant.Constant.CURRENT_TIME;
import static com.mage.magemata.constant.Constant.MAINBAR_INDEX;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.httpGet;

public class MainActivity extends BaseActivity {
    @ViewInject(R.id.activty_navbar)
    private PageNavigationView tab;
    @ViewInject(R.id.activty_viewpager)
    private ViewPager pager;
    private MyViewPagerAdapter adapter;
    @ViewInject(R.id.main_background)
    RelativeLayout background;
    private NavigationController navigationController;
    private String GET_MESSAGE=ROOT_URL+"message";
    private String GET_ITEM_FRESH=ROOT_URL+"circleitem";

    private int index=0;

    //刷新界面
    @Override
    protected void onResume() {
        super.onResume();
        if(getBundle()!=null){
            index=getBundle().getInt(MAINBAR_INDEX);
            navigationController.setSelect(index);

        }
        LOG(index+"");

        getSkin();

    }
    //6.0以下的必须用
//    getResources().getColor(R.color.darkkhaki)
    @Override
    public void initData() {
        ifFreshMssage();
        ifFreshCircle();
        PublicMethod.requestPermission(this);
//        PageNavigationView tab = (PageNavigationView) findViewById(R.id.activty_navbar);
         navigationController = tab.material()
                .addItem(R.mipmap.tab_circle, "圈子",getResources().getColor(R.color.darkkhaki))
                .addItem(R.mipmap.tab_publish, "发布",getResources().getColor(R.color.green))
                .addItem(android.R.drawable.stat_notify_chat, "私聊",getResources().getColor(R.color.hot_pink))
                .addItem(R.mipmap.ico_mychat_selected, "我的",getResources().getColor(R.color.darkblue))
                .setDefaultColor(getResources().getColor(R.color.yellow))//未选中状态的颜色
                .setMode(MaterialMode.HIDE_TEXT )//这里可以设置样式模式，总共可以组合出4种效果
                .build();
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                Log.i("asd","selected: " + index + " old: " + old);
            }

            @Override
            public void onRepeat(int index) {
                navigationController.setHasMessage(index,false);
                Log.i("asd","onRepeat selected: " + index);
            }
        });
        adapter=new MyViewPagerAdapter(getSupportFragmentManager(),navigationController.getItemCount());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        navigationController.setupWithViewPager(pager);

    }

    @Override
    public void loadData() {


    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
        setStatus(tab);


    }



    public static void actionstart(Context context ){
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        private int size;

        private MyViewPagerAdapter(FragmentManager fm,int size) {
            super(fm);
            this.size = size;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 1:
                    return new PublishFragment();
                case 2:
                    return new ChatFragment();
                case 3:
                    return new MoreFragment();

                default:
                    return new CircleFragment();
            }
        }

        @Override
        public int getCount() {
            return size;
        }
    }

    public void getSkin() {
        SharedPreferences skinSettingPreference = MainActivity.this.getSharedPreferences("background", Context.MODE_PRIVATE);
        String back = skinSettingPreference.getString("background", "");
        if (!Objects.equals(back, "")&&!back.contains("resource")) {
            Drawable drawable =RxImageUtils.bitmap2Drawable(RxImageUtils.getBitmap(back)) ;
            background.setBackground(drawable);
        }
        else if(!Objects.equals(back, "")){
            Drawable d= null;
            try {
                d = Drawable.createFromStream(getContentResolver().openInputStream(Uri.parse(back)),null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            background.setBackground(d);
        }
    }
    private void showNotice(String content){
        NotificationManager manager = (NotificationManager) MainActivity.this.getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(MainActivity.this);
        builder.setContentText(content);
        builder.setContentTitle("MageMata新消息");
        builder.setSmallIcon(R.mipmap.logo2);
        builder.setTicker("MageMata新消息");
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());//设置时间，设置为系统当前的时间
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt(MAINBAR_INDEX,2);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
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
    private void ifFreshMssage() {
        httpGet(GET_MESSAGE + "?time=" + getCurrentTime(), new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                String state = null;
                try {
                    state = result.getString("state");
                    switch (state) {
                        case "ok":
                            JSONObject data = result.getJSONObject("data");
                            String content = data.getString("content");
                            LOG(content);
                            showNotice(content);
                            navigationController.setHasMessage(2,true);
                            break;
                        default:
                            LOG(state);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
    private void ifFreshCircle() {
        httpGet(GET_ITEM_FRESH + "?time=" + getCurrentTime(), new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                String state = null;
                try {
                    state = result.getString("state");
                    LOG(state);

                    switch (state) {
                        case "ok":
                            JSONObject data = result.getJSONObject("data");
                            navigationController.setHasMessage(0,true);
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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


    protected String   getCurrentTime(){
        return  MyPrefence.getInstance(MainActivity.this).getString(CURRENT_TIME);
    }
}
