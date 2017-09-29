package com.mage.magemata.user;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mage.magemata.R;
import com.mage.magemata.circle.card.CardActivity;
import com.mage.magemata.main.BaseFragment;
import com.mage.magemata.util.MyPrefence;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;
import com.vondear.rxtools.RxDataUtils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mage.magemata.constant.Constant.FLASH_USER_ID;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.constant.Constant.USER_ID;
import static com.mage.magemata.user.UserInfoActivity.flash_user_id;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.httpGet;

/**
 * Created by Administrator on 2017/5/26.
 */
@ContentView(R.layout.fragment_userinfo)
public class UserPageFragment extends BaseFragment {
    @ViewInject(R.id.userinfo_rv_baselist)
    private RecyclerView mRecyclerView;
    private FollowercyAdapter mFollowerAdapter;
    private static boolean FOLLOERED=false;
    private  ArrayList<User> followlist = new ArrayList<>();

    private String get_follow_url = ROOT_URL+"userfollow/create";
    private String get_fans_url = ROOT_URL+"userfollow/";
    private String get_history_url = ROOT_URL+"userhistory/";


    @Override
    protected void initData() {
        initAdapter();
        Bundle bundle=getArguments();
        int index=bundle.getInt("index");
        switch (index){
            case 0:
                httpGet(get_follow_url + "?user_id=" + flash_user_id, new Callback.CommonCallback<JSONArray>() {
                    @Override
                    public void onSuccess(JSONArray result) {
                        followlist.clear();
                        followlist = new Gson().fromJson(result.toString(), new TypeToken<ArrayList<User>>() {
                        }.getType());
                        LOG(followlist.get(0).account);
                        mFollowerAdapter.setNewData(followlist);
                        mFollowerAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LOG(ex.toString());
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {

                    }
                });
                break;
            case 1:
                httpGet(get_fans_url  + flash_user_id, new Callback.CommonCallback<JSONArray>() {
                    @Override
                    public void onSuccess(JSONArray result) {
                        followlist.clear();
                        followlist = new Gson().fromJson(result.toString(), new TypeToken<ArrayList<User>>() {
                        }.getType());
                        LOG(followlist.get(0).account);
                        mFollowerAdapter.setNewData(followlist);
                        mFollowerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LOG(ex.toString());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                break;
            case 2:
                httpGet(get_history_url  + flash_user_id, new Callback.CommonCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        followlist.clear();
                        try {
                            followlist = new Gson().fromJson(result.getString("data"), new TypeToken<ArrayList<User>>() {}.getType());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mFollowerAdapter.setNewData(followlist);
                        mFollowerAdapter.notifyDataSetChanged();
                        mRecyclerView.setClickable(false);
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LOG(ex.toString());
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {

                    }
                });
                break;
        }
    }

    @Override
    protected void setData() {

    }

    private void initAdapter() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mAppCompatActivity));
        mFollowerAdapter = new FollowercyAdapter();
        mFollowerAdapter.openLoadAnimation();
        mFollowerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putInt("type",1);
                bundle.putString(FLASH_USER_ID,mFollowerAdapter.getItem(position).getId());
                readyGo(UserInfoActivity.class,bundle);
            }
        });
        mRecyclerView.setAdapter(mFollowerAdapter);
    }




    public static Fragment newInstance(int index) {
        UserPageFragment f = new UserPageFragment();
        Bundle args = new Bundle();
        args.putInt("index",index);
        f.setArguments(args);
        return f;
    }

    private class FollowercyAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

        public FollowercyAdapter() {
            super(R.layout.follow_rv_item);
        }
        @Override
        protected void convert(BaseViewHolder helper, User item) {
            //足迹板块
            if(item.getContent()!=null)
            {
                helper.getView(R.id.follow_item_portrait).setVisibility(View.GONE);
                helper.setText(R.id.follow_item_name, item.getContent());
                helper.setText(R.id.follow_item_time, item.getTime());
                helper.getView(R.id.follow_item_moreimage).setVisibility(View.GONE);
                return;
            }
            helper.addOnClickListener(R.id.follow_item_moreimage);
            if(!RxDataUtils.isNullString(item.getProfile())) {
                Picasso.with(mAppCompatActivity)
                        .load(item.getProfile())
                        .resize(100, 100)
                        .centerCrop()
                        .into((CircleImageView) helper.getView(R.id.follow_item_portrait));
            }
            helper.setText(R.id.follow_item_name, item.name);
        }
    }


}

