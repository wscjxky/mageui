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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.mage.magemata.R;
import com.mage.magemata.circle.Circle;
import com.mage.magemata.circle.SlidingUpBaseActivity;
import com.mage.magemata.user.UserInfoActivity;
import com.mage.magemata.util.MyPrefence;
import com.squareup.picasso.Picasso;
import com.vondear.rxtools.RxDataUtils;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mage.magemata.constant.Constant.CIRCLE_ID;
import static com.mage.magemata.constant.Constant.CIRCLE_ITEM_ID;
import static com.mage.magemata.constant.Constant.GET_CIRCLE_ITEM;
import static com.mage.magemata.util.PublicMethod.httpGet;
import static com.mage.magemata.util.PublicMethod.isJsonarray0;

public class Circle_Item_Activity extends SlidingUpBaseActivity<ObservableRecyclerView> implements ObservableScrollViewCallbacks {

    private  ArrayList<Circle_Item> circlelist = new ArrayList<>();
    public static String circle_item_ID;
    private Circle_itemAdapter madapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        StatusBarUtil.setTranslucentForCoordinatorLayout (Circle_Item_Activity.this,0);
        initData();
    }

    private void initData() {
        httpGet(GET_CIRCLE_ITEM+"/"+ MyPrefence.getInstance(this).getInt(CIRCLE_ID),new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                    switch (isJsonarray0(result)){
                        case 0:
                            circlelist = new Gson().fromJson(result, new TypeToken<ArrayList<Circle_Item>>() {}.getType());
                            madapter.setNewData(circlelist);
                            madapter.notifyDataSetChanged();
                            break;
                        case 1:
                            Circle_Item item= new Gson().fromJson(result,Circle_Item.class);
                            madapter.addData(item);
                            break;
                        default:
                            Log.e("没有东西", "Asd");

                    }


            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("sdf", ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    public static void actionstart(Context context){
        Intent intent=new Intent(context,Circle_Item_Activity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_slidinguprecyclerview;
    }

    @Override
    protected ObservableRecyclerView createScrollable() {
        final ObservableRecyclerView recyclerView = (ObservableRecyclerView) findViewById(R.id.scroll);
        recyclerView.setScrollViewCallbacks(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        madapter=new Circle_itemAdapter();
        madapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putInt("type",1);
                UserInfoActivity.actionstart(Circle_Item_Activity.this,bundle);
            }
        });
        madapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyPrefence.getInstance(Circle_Item_Activity.this).saveInt(CIRCLE_ITEM_ID,madapter.getItem(position).getId());
                CardActivity.actionStart(Circle_Item_Activity.this);
            }
        });
        recyclerView.setAdapter(madapter);
        return recyclerView;
    }

    public class Circle_itemAdapter extends BaseQuickAdapter<Circle_Item, BaseViewHolder> {

        public Circle_itemAdapter() {
            super(R.layout.circle_item );
        }

        @Override
        protected void convert(BaseViewHolder helper, Circle_Item item) {
            if(!RxDataUtils.isNullString(item.getUserImage())) {
                Picasso.with(Circle_Item_Activity.this)
                        .load(item.getUserImage())
                        .resize(200, 200)
                        .centerCrop()
                        .into((CircleImageView) helper.getView(R.id.circle_iv_logo));
            }
            helper.setText(R.id.circle_tv_title, item.getTitle());
            helper.setText(R.id.circle_tv_content, item.getContent())
                    .addOnClickListener(R.id.circle_iv_logo);
        }

    }

    @Event(R.id.circle_but_addlink)
    private void addreward(View  view){

        AddCircle_Item_Activity.actionStart(Circle_Item_Activity.this,"悬赏");
    }

    @Event(R.id.circle_but_addphoto)
    private void addphoto(View  view){
        AddCircle_Item_Activity.actionStart(Circle_Item_Activity.this,"图片");
    }@Event(R.id.circle_but_addtext)
    private void addlink(View  view){
        AddCircle_Item_Activity.actionStart(Circle_Item_Activity.this,"链接");
    }@Event(R.id.circle_but_addvideo)
    private void addtext(View  view){
        AddCircle_Item_Activity.actionStart(Circle_Item_Activity.this,"文字");
    }@Event(R.id.circle_but_addreward)
    private void addvideo(View  view){
        AddCircle_Item_Activity.actionStart(Circle_Item_Activity.this,"视频");
    }


}
