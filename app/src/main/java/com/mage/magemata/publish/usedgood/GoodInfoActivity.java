package com.mage.magemata.publish.usedgood;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/9/10.
 */

public class GoodInfoActivity  extends BaseActivity{
    @ViewInject(R.id.offview)
    private View view;
    @Override
    public void initData() {

    }

    @Override
    public void setContentView() {
            setContentView(R.layout.activity_goodinfo);
            setStatus(view);
    }

    public static void actionstart(Context context){
        Intent intent=new Intent(context,GoodInfoActivity.class);
        context.startActivity(intent);
    }
}
