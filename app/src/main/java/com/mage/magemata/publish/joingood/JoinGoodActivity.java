package com.mage.magemata.publish.joingood;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;

/**
 * Created by Administrator on 2017/5/14.
 */
public class JoinGoodActivity extends BaseActivity {
    @ViewInject(R.id.base_rv)
    RecyclerView recyclerView;
    private JoinGoodApater joinGoodApater;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_joingood);
        initToolbar("凑单",true);
    }

    @Override
    public void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        joinGoodApater = new JoinGoodApater();
        joinGoodApater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                readyGo(JoinGoodInfoActivity.class);
            }
        });
        recyclerView.setAdapter(joinGoodApater);
    }

    @Override
    public void loadData() {

    }
    @Event(R.id.joingood_btn_addgood)
    private void addgood(View view){
        readyGo(AddJoinGoodActivity.class);
    }



    private class JoinGoodApater extends BaseQuickAdapter<JoinGood, BaseViewHolder> {

        public JoinGoodApater() {
            super(R.layout.rv_item_joingood, getSampleData());
        }

        @Override
        protected void convert(BaseViewHolder helper, JoinGood item) {
//        helper.addOnClickListener(R.id.follow_item_moreimage);
//            helper.setImageBitmap(R.id.joingood_item_shoplogo,item.getShopLogo());
            helper.setText(R.id.joingood_item_shopname, item.getShopname());
            helper.setText(R.id.joingood__item_goodsort, item.getShopSort());
            helper.setText(R.id.joingood__item_totalprice, item.getTotalPrice());
            helper.setChecked(R.id.joingood_item_checkbox, item.isEnough());
        }


    }
    private List<JoinGood> getSampleData() {
        List<JoinGood> list = new ArrayList<>();
        JoinGood status = new JoinGood();
        status.setShopname("首尔之心");
        status.setShopsort("首饰");
        status.setTotalPrice("5000");
        status.setIntroduction("首尔(谚文:서울;英文:Seoul)，全称首尔特别市(谚文:서울특별시/汉字:서울特别市)，旧称汉城。");
        JoinGood status1 = new JoinGood();
        status1.setShopname("蓝影态");
        status1.setShopsort("服装");
        status1.setTotalPrice("10000");
        status1.setCurrentPrice("10000");

        status1.setIntroduction("一个软萌妹子");
        list.add(status);
        list.add(status1);
        return list;
    }
}
