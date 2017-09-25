package com.mage.magemata.publish.usedgood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.GoogleDotView;
import com.mage.magemata.R;
import com.mage.magemata.circle.Circle;
import com.mage.magemata.circle.CircleFragment;
import com.mage.magemata.circle.card.Circle_Item_Activity;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.user.UserInfoActivity;
import com.mage.magemata.util.MyPrefence;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;
import static com.mage.magemata.constant.Constant.CIRCLE_ID;
import static com.mage.magemata.constant.Constant.GET_CIRCLE;
import static com.mage.magemata.constant.Constant.GOOD_ID;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.httpGet;

/**
 * Created by Administrator on 2017/9/10.
 */

public class UsedGoodActivity extends BaseActivity {
    @ViewInject(R.id.used_search_view)
    private MaterialSearchView searchView;
    @ViewInject(R.id.used_toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.used_rv)
    private RecyclerView recyclerView;
    @ViewInject(R.id.refreshLayout)
    private TwinklingRefreshLayout mRefreshLayout;
    private UsedGoodAdapter usedGoodAdapter;
    private  String GET_URL=ROOT_URL+"good/";
    private  ArrayList<Good> goodlist = new ArrayList<>();
    @Override
    public void initData() {
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("二手市场");
        setSupportActionBar(toolbar);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        usedGoodAdapter = new UsedGoodAdapter();
        usedGoodAdapter.openLoadAnimation(SCALEIN);
        usedGoodAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CURRENT_GOOD_ID=usedGoodAdapter.getItem(position)
                        .getGood_id();
                readyGo(GoodInfoActivity.class);
            }
        });
        recyclerView.setAdapter(usedGoodAdapter);
        searchView.setEllipsize(true);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchData(query);
                    return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LOG(newText);
                return false;
            }
        });

    }
    @Event(R.id.usedgood_btn_addgood)
    private void addGood(View view){
        readyGo(AddGoodActivity.class);
    }
    @Override
    public void loadData() {
        mRefreshLayout.setHeaderView(new GoogleDotView(UsedGoodActivity.this));
        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setEnableOverScroll(true);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                httpGet(GET_URL, new Callback.CommonCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        try {
                            String state=result.getString("state");
                            switch (state){
                                case "ok":
                                    goodlist.clear();
                                    goodlist = new Gson().fromJson(result.getString("data"), new TypeToken<ArrayList<Good>>() {}.getType());
                                    break;
                                default:
                                    LOG("error");
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
                usedGoodAdapter.setNewData(goodlist);
                usedGoodAdapter.notifyDataSetChanged();
            }
        });
    }

    public void searchData(String text){
        httpGet(GET_URL+"create?good_name="+text, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    String state=result.getString("state");
                    switch (state){
                        case "ok":
                            goodlist.clear();
                            goodlist = new Gson().fromJson(result.getString("data"), new TypeToken<ArrayList<Good>>() {}.getType());
                            break;
                        case "empty":
                            goodlist.clear();
                            showSuccToast(R.string.emptysearch);
                            break;
                        default:
                            LOG("error");
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                mRefreshLayout.finishRefreshing();
            }
        });

    }
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_used);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }


    public class UsedGoodAdapter extends BaseQuickAdapter<Good, BaseViewHolder> {
        public UsedGoodAdapter() {
            super(R.layout.used_good_item);
        }
        @Override
        protected void convert(BaseViewHolder helper, Good i) {

            helper.setText(R.id.good_name, i.getGood_name());

            Picasso.with(UsedGoodActivity.this)
                    .load(i.getImage())
                    .resize(120, new Random().nextInt(20)+110)
                    .centerCrop()
                    .into( (ImageView)helper.getView(R.id.good_item_logo));
            helper.setText(R.id.good_user, i.getUser_name());
            helper.setText(R.id.good_time, i.getCreatetime());

        }

    }

}
