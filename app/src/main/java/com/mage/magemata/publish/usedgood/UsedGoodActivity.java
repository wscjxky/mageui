package com.mage.magemata.publish.usedgood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mage.magemata.R;
import com.mage.magemata.circle.Circle;
import com.mage.magemata.main.BaseActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

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
    private CircleFragAdapter circleFragAdapter;

    @Override
    public void initData() {
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("二手市场");
        setSupportActionBar(toolbar);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        circleFragAdapter = new CircleFragAdapter();
        circleFragAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodInfoActivity.actionstart(UsedGoodActivity.this);
            }
        });
        recyclerView.setAdapter(circleFragAdapter);
        searchView.setEllipsize(true);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    @Override
    public void loadData() {

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
    public static void actionStart(Context context){
        Intent intent=new Intent(context,UsedGoodActivity.class);
        context.startActivity(intent);
    }

    private   List<Circle> getSampleData() {
        List<Circle> list = new ArrayList<>();

        for(int i=0 ;i<10;i++){
            Circle status = new Circle();
            status.setTitle("物品"+i);
            status.setIntroduction("物品的描述");

            list.add(status);

        }

        return list;
    }
    public class CircleFragAdapter extends BaseQuickAdapter<Circle, BaseViewHolder> {
        private  boolean stage=true;
        public CircleFragAdapter() {
            super(R.layout.used_good_item, getSampleData());
        }

        @Override
        protected void convert(BaseViewHolder helper, Circle item) {
//        helper.addOnClickListener(R.id.follow_item_moreimage);
            if(stage){
                helper.setImageResource(R.id.good_item_logo,R.drawable.photo5);
                stage=false;
            }
            else {
                stage=true;
                helper.setImageResource(R.id.good_item_logo, R.drawable.food4);
            }

            helper.setText(R.id.good_name, item.getTitle());
        }

    }

}
