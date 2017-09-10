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
import com.mage.magemata.R;
import com.sackcentury.shinebuttonlib.ShineButton;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */
public class UserPageFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Activity activity;
    private FollowercyAdapter mFollowerAdapter;
    private static boolean FOLLOERED=false;
    private Button addFollower;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_userinfo, container, false);
        return mRecyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity=getActivity();
        initRecyclerView();
        initAdapter();
    }




    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }
    private void initAdapter() {
        mFollowerAdapter = new FollowercyAdapter();
        mFollowerAdapter.openLoadAnimation();
        mFollowerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        mRecyclerView.setAdapter(mFollowerAdapter);
    }




    public static Fragment newInstance() {
        return new UserPageFragment();
    }

    private class FollowercyAdapter extends BaseQuickAdapter<Follower, BaseViewHolder> {

        public FollowercyAdapter() {
            super(R.layout.follow_rv_item, getSampleData(10));
        }

        @Override
        protected void convert(BaseViewHolder helper, Follower item) {
            helper.addOnClickListener(R.id.follow_item_moreimage);
            helper.setBackgroundRes(R.id.follow_item_portrait,item.getUserPortrait());
            helper.setText(R.id.follow_item_name, item.getUserName());
            helper.setText(R.id.follow_item_grade, item.getUserGrade());
            helper.setText(R.id.follow_item_introduce, item.getUserIntroduce());
        }


    }
    private  static List<Follower> getSampleData(int lenth) {
        List<Follower> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            Follower status = new Follower();
            status.setUserName("蓝影态" + i);
            status.setCreatedAt("04/05/" + i);
            status.setUserGrade("大一新生");
            status.setUserIntroduce("一个软萌妹子");
            list.add(status);
        }
        return list;
    }

}

