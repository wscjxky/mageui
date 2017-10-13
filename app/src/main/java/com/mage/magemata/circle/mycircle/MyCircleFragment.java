package com.mage.magemata.circle.mycircle;

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
import com.mage.magemata.circle.card.CircleActivity;
import com.mage.magemata.main.BaseFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;
import static com.mage.magemata.constant.Constant.ITEM_LOGO_SIZE;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.util.PublicMethod.httpGet;


/**
 * Created by Administrator on 2017/5/4.
 */
@ContentView(R.layout.fragment_base)
public class MyCircleFragment extends BaseFragment {
    @ViewInject(R.id.base_rv)
    private RecyclerView recyclerView;
    private CircleAdapter circleFragAdapter;
    private  String sub_url=ROOT_URL+"usersubscribe/create?user_id=";
    private ArrayList<Circle> circlelist = new ArrayList<>();


    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mAppCompatActivity));
        circleFragAdapter  = new CircleAdapter();
        circleFragAdapter.openLoadAnimation(SCALEIN);
        circleFragAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                setCircleId(circleFragAdapter.getItem(position).getCircle_id());
                readyGo(CircleActivity.class);
            }
        });
        recyclerView.setAdapter(circleFragAdapter);
    }
    @Override
    protected void setData() {
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



    private class CircleAdapter extends BaseQuickAdapter<Circle, BaseViewHolder> {
        CircleAdapter() {

            super(R.layout.circle_frg_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, Circle item) {

            helper.setText(R.id.circlef_tv_content, item.getContent());
            Picasso.with(mAppCompatActivity)
                    .load(item.getImage())
                    .resize(ITEM_LOGO_SIZE,ITEM_LOGO_SIZE)
                    .centerCrop()
                    .into( (ImageView)helper.getView(R.id.circlef_iv_logo));
            helper.setText(R.id.circlef_tv_title, item.getTitle());
        }
    }



}