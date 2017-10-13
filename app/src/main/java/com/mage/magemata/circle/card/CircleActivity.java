package com.mage.magemata.circle.card;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.user.UserInfoActivity;
import com.mage.magemata.util.MyPrefence;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;
import com.vondear.rxtools.RxDataUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static com.mage.magemata.constant.Constant.CIRCLE_ITEM_ID;
import static com.mage.magemata.constant.Constant.FLASH_USER_ID;
import static com.mage.magemata.constant.Constant.ITEM_PROFILE_SIZE;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.constant.Constant.T_CIRCLE_ID;
import static com.mage.magemata.constant.Constant.USER_ID;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.getMap;
import static com.mage.magemata.util.PublicMethod.httpGet;
import static com.mage.magemata.util.PublicMethod.httpPost;

/**
 * Created by Administrator on 2017/10/11.
 */

public class CircleActivity extends BaseActivity {
    @ViewInject(R.id.circle1_rv)
    private RecyclerView recyclerView;
    @ViewInject(R.id.circle_item_menu)
    FloatingActionsMenu menu;
    @ViewInject(R.id.circle_btn_subscribe)
    ShineButton button_heart;
    private CircleAdapter madapter;
    private  String GET_URL=ROOT_URL+"circleitem/";
    private String subscribe_url=ROOT_URL+"usersubscribe";

    private  ArrayList<Circle_Item> circlelist = new ArrayList<>();
    @Override
    public void initData() {
        button_heart.init(this);
        button_heart.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                Map<String, String> map = getMap();
                map.put(USER_ID, getUserId());
                map.put(T_CIRCLE_ID, getCircleId() + "");
                httpPost(subscribe_url, map, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (Objects.equals(result, "ok")) {
                            Toasty.success(CircleActivity.this,"关注成功" + "").show();
                        } else {
                            Toasty.error(CircleActivity.this, "取消关注").show();
                            button_heart.setChecked(false);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }});
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        madapter = new CircleAdapter();
        madapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putInt("type",1);
                bundle.putString(FLASH_USER_ID, madapter.getItem(position).user_id);

                UserInfoActivity.actionstart(CircleActivity.this,bundle);
            }
        });
        madapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyPrefence.getInstance(CircleActivity.this).saveInt(CIRCLE_ITEM_ID,madapter.getItem(position).getId());
                CardActivity.actionStart(CircleActivity.this);
            }
        });
        recyclerView.setAdapter(madapter);
    }

    @Override
    public void loadData() {
        httpGet(GET_URL+ getCircleId(),new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                    try {
                        String state=result.getString("state");
                        switch (state){
                            case "ok":
                                LOG(state);
                                circlelist = new Gson().fromJson(result.getString("data"), new TypeToken<ArrayList<Circle_Item>>() {}.getType());
                                madapter.setNewData(circlelist);
                                madapter.notifyDataSetChanged();
                                break;
                            default:
                                showErrorToast(R.string.checkInternet);
                                LOG("error");
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("sdf", ex.toString());
                showErrorToast(R.string.checkInternet);

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Event(R.id.circle_btn_addlink)
    private void addlink(View view){
        AddCircle_Item_Activity.actionStart(CircleActivity.this,"链接");
        menu.collapse();
    }

    @Event(R.id.circle_btn_addphoto)
    private void addphoto(View  view){
        AddCircle_Item_Activity.actionStart(CircleActivity.this,"图片");
        menu.collapse();

    }@Event(R.id.circle_btn_addtext)
    private void addtext(View  view){
        AddCircle_Item_Activity.actionStart(CircleActivity.this,"文字");
        menu.collapse();
    }
//    @Event(R.id.circle_btn_addvideo)
//    private void addvideo(View  view){
////        AddCircle_Item_Activity.actionStart(Circle_Item_Activity.this,"文字");
////        menu.collapse();
//
//    }
    @Event(R.id.circle_btn_addreward)
    private void addreward(View  view){
//        AddCircle_Item_Activity.actionStart(Circle_Item_Activity.this,"视频");
//        menu.collapse();

    }


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_circle1);
        initToolbar(getCircleTitle(),true);

    }


    public class CircleAdapter extends BaseQuickAdapter<Circle_Item, BaseViewHolder> {
        public CircleAdapter() {
            super(R.layout.circle_item);
        }
        @Override
        protected void convert(BaseViewHolder helper, Circle_Item item) {
            if(!RxDataUtils.isNullString(item.getUserImage())) {
                Picasso.with(CircleActivity.this)
                        .load(item.getUserImage())
                        .resize(ITEM_PROFILE_SIZE, ITEM_PROFILE_SIZE)
                        .centerCrop()
                        .into((CircleImageView) helper.getView(R.id.circle_iv_logo));
            }
            helper.setText(R.id.circle_tv_username, item.user_name);

            helper.setText(R.id.circle_tv_title, item.getTitle());
            helper.setText(R.id.circle_tv_content, item.getContent())
                    .addOnClickListener(R.id.circle_iv_logo);
        }
        }

}

