package com.mage.magemata.trash;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mage.magemata.R;
import com.mage.magemata.circle.Circle;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.util.MyPrefence;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;
import static com.mage.magemata.constant.Constant.CIRCLE_ID;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.util.PublicMethod.httpGet;

/**
 * Created by Administrator on 2017/9/14.
 */

public class MyCircleActivity1 extends BaseActivity {
    @ViewInject(R.id.base_rv)
    private RecyclerView recyclerView;
    private CircleAdapter circleFragAdapter;
    private  String sub_url=ROOT_URL+"usersubscribe/create?user_id=";
    private ArrayList<Circle> circlelist = new ArrayList<>();

    @Override
    public void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        circleFragAdapter  = new CircleAdapter();
        circleFragAdapter.openLoadAnimation(SCALEIN);
        circleFragAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyPrefence.getInstance(MyCircleActivity1.this).saveInt(CIRCLE_ID,circleFragAdapter.getItem(position).getCircle_id());
                Circle_Item_Activity.actionstart(MyCircleActivity1.this);
            }
        });
        recyclerView.setAdapter(circleFragAdapter);
    }

    @Override
    public void loadData() {
        httpGet(sub_url+getUserId(), new Callback.CommonCallback<JSONArray>() {
            @Override
            public void onSuccess(JSONArray result) {
                circlelist.clear();
                circlelist = new Gson().fromJson(result.toString(), new TypeToken<ArrayList<Circle>>() {}.getType());
                circleFragAdapter.setNewData(circlelist);
                circleFragAdapter.notifyDataSetChanged();

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("sdf",ex.toString());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mycircle);
        initToolbar("我的圈子",true);
    }
    private class CircleAdapter extends BaseQuickAdapter<Circle, BaseViewHolder> {
        CircleAdapter() {
            super(R.layout.circle_frg_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, Circle item) {

            helper.setText(R.id.circlef_tv_content, item.getContent());
            Picasso.with(MyCircleActivity1.this)
                    .load(item.getImage())
                    .resize(90, 90)
                    .centerCrop()
                    .into( (ImageView)helper.getView(R.id.circlef_iv_logo));
            helper.setText(R.id.circlef_tv_title, item.getTitle());
        }
    }
}
