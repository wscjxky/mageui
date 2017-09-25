package com.mage.magemata.publish;

import android.app.Activity;
import android.view.View;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseFragment;
import com.mage.magemata.publish.delivery.DeliveryActivity;
import com.mage.magemata.publish.usedgood.UsedGoodActivity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by Administrator on 2017/4/24.
 */

@ContentView(R.layout.fragment_publish)
public class PublishFragment extends BaseFragment {

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Event(R.id.publish_iv_lost)
    private void lost(View view){
        //                LostActivity.actionStart(activity);
    }
    @Event(R.id.publish_iv_used)
    private void user(View view){
        readyGo(UsedGoodActivity.class);
    }
    @Event(R.id.publish_iv_join)
    private void join(View view){
//                JoinGoodActivity.actionStart(activity);
    }
    @Event(R.id.publish_iv_print)
    private void print(View view){
        //                LostActivity.actionStart(activity);
    }
    @Event(R.id.publish_iv_findwork)
    private void findword(View view){
            readyGo(DeliveryActivity.class);
    }


}
