package com.mage.magemata.circle;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mage.magemata.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */

public class CircleFragAdapter extends BaseQuickAdapter<Circle, BaseViewHolder> {

    public CircleFragAdapter() {
        super(R.layout.circle_frg_item, getSampleData());
    }

    @Override
    protected void convert(BaseViewHolder helper, Circle item) {
//        helper.addOnClickListener(R.id.follow_item_moreimage);
        if(item.getLogo()!=null) {
            helper.setImageBitmap(R.id.circlef_iv_logo, item.getLogo());
        }
        helper.setText(R.id.circlef_tv_title, item.getTitle());
        helper.setText(R.id.circlef_tv_desc, item.getIntroduction());
    }

    private  static List<Circle> getSampleData() {
        List<Circle> list = new ArrayList<>();

        for(int i=0 ;i<10;i++){
            Circle status = new Circle();
            status.setTitle("哈利波特"+i);
            status.setIntroduction("哈利波特的内容");

            list.add(status);

        }
        Circle status1 = new Circle();
        status1.setTitle("二次元文学爱好者");
        status1.setIntroduction("二次元文学爱好者的内容二次元文学爱好者的内容二次元文学爱好者的内容");

        list.add(status1);
        return list;
    }
}