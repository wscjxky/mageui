package com.mage.magemata.circle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.GoogleDotView;
import com.mage.magemata.R;
import com.mage.magemata.circle.card.Circle_Item_Activity;
import com.mage.magemata.login.LoginActivity;
import com.mage.magemata.main.BaseFragment;
import com.mage.magemata.util.MyPrefence;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;


import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;
import static com.mage.magemata.constant.Constant.CIRCLE_ID;
import static com.mage.magemata.constant.Constant.GET_CIRCLE;
import static com.mage.magemata.util.PublicMethod.httpGet;

/**
 * Created by Administrator on 2017/4/24.
 */
@ContentView(R.layout.fragment_circle)
public class CircleFragment extends BaseFragment {
    @ViewInject(R.id.fg_circle_rv)
    private RecyclerView recyclerView;
    @ViewInject(R.id.refreshLayout)
    private TwinklingRefreshLayout mRefreshLayout;
    @ViewInject(R.id.fgcircle_menu)
    FloatingActionsMenu fb_menu;

    private  ArrayList<Circle> circlelist = new ArrayList<>();
    private CircleFragAdapter circleFragAdapter;





    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mAppCompatActivity));
        circleFragAdapter  = new CircleFragAdapter();
        circleFragAdapter.openLoadAnimation(SCALEIN);
        circleFragAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyPrefence.getInstance(mAppCompatActivity).saveInt(CIRCLE_ID,circleFragAdapter.getItem(position).getCircle_id());
                Circle_Item_Activity.actionstart(mAppCompatActivity);
            }
        });
        mRefreshLayout.setHeaderView(new GoogleDotView(mAppCompatActivity));
        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setEnableOverScroll(true);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                httpGet(GET_CIRCLE, new Callback.CommonCallback<JSONArray>() {
                    @Override
                    public void onSuccess(JSONArray result) {
                        circlelist.clear();
                        circlelist = new Gson().fromJson(result.toString(), new TypeToken<ArrayList<Circle>>() {}.getType());
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
                        refreshLayout.finishRefreshing();
                    }
                });
            }
            @Override
            public void onFinishRefresh() {
                circleFragAdapter.setNewData(circlelist);
                circleFragAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(circleFragAdapter);
    }




    //
    @Event(R.id.fgcircle_btn_addcircle)
    private void addCircle(View view){
        fb_menu.collapse();

        AddCircleActivity.actionStart(mAppCompatActivity);
//        mRefreshLayout.startRefresh();
//        AddCircleActivity.actionStart(activity);
    }
    @Event(R.id.fgcircle_btn_mycircle)
    private void myCircle(View view) {
        fb_menu.collapse();

        readyGo(MyCircleActivity.class);

    }


//        MyCircleActivity.actionStart(activity);
//        floatingActionsMenu.collapse();






    private class CircleFragAdapter extends BaseQuickAdapter<Circle, BaseViewHolder> {
        CircleFragAdapter() {
            super(R.layout.circle_frg_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, Circle item) {
            helper.setText(R.id.circlef_tv_content, item.getContent());
            Picasso.with(mAppCompatActivity)
                    .load(item.getImage())
                    .resize(90, 90)
                    .centerCrop()
                    .into( (ImageView)helper.getView(R.id.circlef_iv_logo));
            helper.setText(R.id.circlef_tv_title, item.getTitle());
        }
    }

}
