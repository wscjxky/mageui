package com.mage.magemata.publish.lostfound;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseFragment;
import com.mage.magemata.publish.usedgood.Good;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;
import static com.mage.magemata.util.PublicMethod.LOG;


/**
 * Created by Administrator on 2017/5/4.
 */

@ContentView(R.layout.fragment_lostfound)
public class LostFragment extends BaseFragment {
    @ViewInject(R.id.base_rv)
    RecyclerView recyclerView;
    private FoundAdapter adapter;

    @Override
    protected void initData() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mAppCompatActivity));
        adapter = new FoundAdapter();
        adapter.openLoadAnimation(SCALEIN);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        adapter.addData(new Good());
        adapter.addData(new Good());

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setData() {

    }

    @Event(R.id.fg_lostfound_addbutton)
    private void add(View view){
        readyGo(AddLostFoundActivity.class);
    }
    private class FoundAdapter extends BaseQuickAdapter<Good, BaseViewHolder> {
        FoundAdapter() {
            super(R.layout.item_foundlost);
        }

        @Override
        protected void convert(BaseViewHolder helper, Good item) {
//            helper.setText(R.id.base_tv_title,"SAD");
//            Picasso.with(activity)
//                    .load(item.getImage())
//                    .resize(90, 90)
//                    .centerCrop()
//                    .into( (ImageView)helper.getView(R.id.circlef_iv_logo));
//            helper.setText(R.id.circlef_tv_title, item.getTitle());
        }
    }
}