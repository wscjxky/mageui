package com.mage.magemata.publish;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mage.magemata.R;
import com.mage.magemata.publish.usedgood.UsedGoodActivity;


/**
 * Created by Administrator on 2017/4/24.
 */

public class PublishFragment extends Fragment {
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_publish, container, false);
        activity=getActivity();
        setListener(mview);
        return mview;
    }

    private  void setListener(View mview){
        mview.findViewById(R.id.publish_iv_lost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LostActivity.actionStart(activity);
            }
        });
        mview.findViewById(R.id.publish_iv_used).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsedGoodActivity.actionStart(activity);
            }
        });
        mview.findViewById(R.id.publish_iv_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                JoinGoodActivity.actionStart(activity);
            }
        });
        mview.findViewById(R.id.publish_iv_print).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PrintActivity.actionStart(activity);
            }
        });
        mview.findViewById(R.id.publish_iv_findwork).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DeliveryActivity.actionStart(activity);
            }
        });
    }

}
