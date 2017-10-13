package com.mage.magemata.circle;

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
import com.mage.magemata.circle.card.CircleActivity;
import com.mage.magemata.circle.mycircle.*;
import com.mage.magemata.main.BaseFragment;
import com.squareup.picasso.Picasso;
import com.vondear.rxtools.view.dialog.RxDialogEditSureCancel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;


import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;
import static com.mage.magemata.constant.Constant.GET_CIRCLE;
import static com.mage.magemata.constant.Constant.ITEM_LOGO_SIZE;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.util.PublicMethod.LOG;
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
    private String search_url=ROOT_URL+"circle/create?title=";




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
                setCircleId(circleFragAdapter.getItem(position).getCircle_id());
                setCircleTitle(circleFragAdapter.getItem(position).getTitle());


                readyGo(CircleActivity.class);
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
    @Event(R.id.fgcircle_btn_search)
    private void search(View view) {
        fb_menu.collapse();
        final RxDialogEditSureCancel rxDialogEditSureCancel = new RxDialogEditSureCancel(mAppCompatActivity);//提示弹窗
        rxDialogEditSureCancel.getEditText().setHint("搜索标题");
        rxDialogEditSureCancel.getTvSure().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCircle(rxDialogEditSureCancel.getEditText().getText().toString());
                rxDialogEditSureCancel.cancel();
            }
        });
        rxDialogEditSureCancel.getTvCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogEditSureCancel.cancel();
            }
        });
        rxDialogEditSureCancel.show();
    }

    private void searchCircle(String text) {
        httpGet(search_url+text, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    String state=result.getString("state");
                    switch (state){
                        case "ok":
                            circlelist.clear();
                            circlelist = new Gson().fromJson(result.getString("data"), new TypeToken<ArrayList<Circle>>() {}.getType());
                            break;
                        case "empty":
                            circlelist.clear();
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
                    .resize(ITEM_LOGO_SIZE, ITEM_LOGO_SIZE)
                    .centerCrop()
                    .into( (ImageView)helper.getView(R.id.circlef_iv_logo));
            helper.setText(R.id.circlef_tv_title, item.getTitle());
        }
    }

}
