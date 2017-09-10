package com.mage.magemata;

import android.app.Application;

import com.mage.magemata.util.PublicMethod;
import com.vondear.rxtools.RxUtils;

import org.xutils.x;

/**
 * Created by Administrator on 2017/9/7.
 */


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);

        RxUtils.init(this);
        x.Ext.init(this);
        // Normal app init code...
    }
}