package com.mage.magemata;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.mage.magemata.util.MyPrefence;
import com.vondear.rxtools.RxUtils;

import org.xutils.x;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.mage.magemata.constant.Constant.CURRENT_TIME;
import static com.mage.magemata.util.PublicMethod.LOG;

/**
 * Created by Administrator on 2017/9/7.
 */


public class App extends Application {
    public static  String APP_DIR;

    @Override
    public void onCreate() {
        super.onCreate();
//        APP_DIR= getApplicationContext().getFilesDir().getAbsolutePath();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        //储存时间
        setCurrentTime();

        RxUtils.init(this);
        x.Ext.init(this);
        OkGo.getInstance().init(this);

        // Normal app init code...
    }
    protected void setCurrentTime (){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currenttime=sdf.format(new Date());
        LOG(currenttime);
        MyPrefence.getInstance(this).saveString(CURRENT_TIME,currenttime);

    }


}