/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mage.magemata.circle.card;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.jaeger.library.StatusBarUtil;
import com.mage.magemata.R;
import com.mage.magemata.circle.SlidingUpBaseActivity;
import com.mage.magemata.user.UserInfoActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class CircleActivity extends SlidingUpBaseActivity<ObservableRecyclerView> implements ObservableScrollViewCallbacks {


    private CircleAdapter circleAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        StatusBarUtil.setTranslucentForCoordinatorLayout (CircleActivity.this,0);

    }
    public static void actionstart(Context context){
        Intent intent=new Intent(context,CircleActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_slidinguprecyclerview;
    }

    @Override
    protected ObservableRecyclerView createScrollable() {
        ObservableRecyclerView recyclerView = (ObservableRecyclerView) findViewById(R.id.scroll);
        recyclerView.setScrollViewCallbacks(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        circleAdapter=new CircleAdapter();
        circleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("asd", "onItemChildClick: ");
                Toast.makeText(CircleActivity.this, "onItemChildClick" + position, Toast.LENGTH_SHORT).show();
            }
        });
        circleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    CardActivity.actionStart(CircleActivity.this);
            }
        });


        recyclerView.setAdapter(circleAdapter);
        return recyclerView;
    }

    public static class CircleAdapter extends BaseQuickAdapter<CardMessage, BaseViewHolder> {

        public CircleAdapter() {

            super(R.layout.circle_item, getSampleData());
        }

        @Override
        protected void convert(BaseViewHolder helper, CardMessage item) {
            if(item.getBitmap()!=null) {
                helper.setImageBitmap(R.id.circle_iv_logo, item.getBitmap());
            }
            helper.setText(R.id.circle_tv_title, item.getTitle());
            helper.setText(R.id.circle_tv_content, item.getContent())
                    .addOnClickListener(R.id.circle_iv_logo);
        }
        private  static List<CardMessage> getSampleData() {
            List<CardMessage> list = new ArrayList<>();

            for(int i=0 ;i<10;i++){
                CardMessage status = new CardMessage();
                status.setTitle("哈利波特"+i);
                status.setContent("哈利波特的内容");
                list.add(status);
            }
            return list;
        }
    }
    @Event(R.id.circle_but_addlink)
    private void addreward(View  view){
        AddCardActivity.actionStart(CircleActivity.this,"悬赏");
    }

    @Event(R.id.circle_but_addphoto)
    private void addphoto(View  view){
        AddCardActivity.actionStart(CircleActivity.this,"图片");
    }@Event(R.id.circle_but_addtext)
    private void addlink(View  view){
        AddCardActivity.actionStart(CircleActivity.this,"链接");
    }@Event(R.id.circle_but_addvideo)
    private void addtext(View  view){
        AddCardActivity.actionStart(CircleActivity.this,"文字");
    }@Event(R.id.circle_but_addreward)
    private void addvideo(View  view){
        AddCardActivity.actionStart(CircleActivity.this,"视频");
    }
}
