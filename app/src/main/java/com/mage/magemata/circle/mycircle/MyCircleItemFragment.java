package com.mage.magemata.circle.mycircle;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mage.magemata.R;
import com.mage.magemata.circle.card.CardActivity;
import com.mage.magemata.circle.card.Circle_Item;
import com.mage.magemata.main.BaseFragment;
import com.mage.magemata.publish.lostfound.AddLostFoundActivity;
import com.mage.magemata.user.UserInfoActivity;
import com.squareup.picasso.Picasso;
import com.vondear.rxtools.RxDataUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;
import static com.mage.magemata.constant.Constant.FLASH_USER_ID;
import static com.mage.magemata.constant.Constant.ITEM_PROFILE_SIZE;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.httpDel;
import static com.mage.magemata.util.PublicMethod.httpGet;


/**
 * Created by Administrator on 2017/5/4.
 */
@ContentView(R.layout.fragment_base)
public class MyCircleItemFragment extends BaseFragment {
    @ViewInject(R.id.base_rv)
    RecyclerView recyclerView;
    private String DEL_URL=ROOT_URL+"circleitem/";
    private CircleItemAdapter madapter;
    private String GET_ITEM_URL=DEL_URL+"create?user_id=";
    private  ArrayList<Circle_Item> itemlist = new ArrayList<>();

    @Override
    protected void initData() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mAppCompatActivity));
        madapter  = new CircleItemAdapter();
        madapter.openLoadAnimation(SCALEIN);
        madapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putInt("type",1);
                bundle.putString(FLASH_USER_ID, madapter.getItem(position).user_id);
                UserInfoActivity.actionstart(mAppCompatActivity,bundle);
            }
        });
        madapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                setCircleItemId(madapter.getItem(position).getId());
                CardActivity.actionStart(mAppCompatActivity);
            }
        });
        madapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                final int id=madapter.getItem(position).circle_item_id;
                showConfirmDialog("确认删除吗?", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        httpDel(DEL_URL+id, new Callback.CommonCallback<JSONObject>() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                showSuccToast(R.string.success_delete);
                                madapter.remove(position);
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
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
                return false;
            }
        });

        recyclerView.setAdapter(madapter);
    }
    @Override
    protected void setData() {
        httpGet(GET_ITEM_URL+ getUserId(),new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    String state = result.getString("state");
                    switch (state){
                        case "ok":
                            itemlist = new Gson().fromJson(result.getString("data"), new TypeToken<ArrayList<Circle_Item>>() {}.getType());
                            madapter.setNewData(itemlist);
                            madapter.notifyDataSetChanged();
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

    @Event(R.id.fg_lostfound_addbutton)
    private void add(View view){
        readyGo(AddLostFoundActivity.class);
    }



    private class CircleItemAdapter extends BaseQuickAdapter<Circle_Item, BaseViewHolder> {
        CircleItemAdapter() {

            super(R.layout.circle_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, Circle_Item item) {
            if(!RxDataUtils.isNullString(item.getUserImage())) {
                Picasso.with(mAppCompatActivity)
                        .load(item.getUserImage())
                        .resize(ITEM_PROFILE_SIZE, ITEM_PROFILE_SIZE)
                        .centerCrop()
                        .into((CircleImageView) helper.getView(R.id.circle_iv_logo));
            }
            helper.setText(R.id.circle_tv_username, item.user_name);
            helper.setText(R.id.circle_tv_title, item.getTitle());
            helper.setText(R.id.circle_tv_content, item.getContent());
        }
    }


}
