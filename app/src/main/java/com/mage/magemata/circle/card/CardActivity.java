package com.mage.magemata.circle.card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.user.UserInfoActivity;
import com.mage.magemata.util.MyPrefence;
import com.squareup.picasso.Picasso;
import com.vondear.rxtools.RxDataUtils;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;
import static com.mage.magemata.constant.Constant.CIRCLE_ITEM_ID;
import static com.mage.magemata.constant.Constant.FLASH_USER_ID;
import static com.mage.magemata.constant.Constant.GET_CIRCLE_ITEM;
import static com.mage.magemata.constant.Constant.POST_CIRCLE_ITEM_COMMENT;
import static com.mage.magemata.constant.Constant.USER_ID;
import static com.mage.magemata.util.PublicMethod.LOG;
import static com.mage.magemata.util.PublicMethod.httpGet;
import static com.mage.magemata.util.PublicMethod.httpPost;
import static com.mage.magemata.util.PublicMethod.isJsonarray0;
import static com.vondear.rxtools.RxKeyboardUtils.hideSoftInput;

/**
 * Created by Administrator on 2017/9/8.
 */

public class CardActivity  extends BaseActivity {
    @ViewInject(R.id.card_et_comment)
    EditText comment;
    @ViewInject(R.id.card_rv_comment)
    RecyclerView recyclerView;
    @ViewInject(R.id.card_tv_count)
    TextView count;
    @ViewInject(R.id.card_tv_content)
    TextView content;
    @ViewInject(R.id.card_tv_type)
    TextView type;
    @ViewInject(R.id.card_tv_time)
    TextView time;

    private int circle_item_id=MyPrefence.getInstance(CardActivity.this).getInt(CIRCLE_ITEM_ID);
    private  ArrayList<Circle_Item_Comment> circlelist = new ArrayList<>();

    private Circle_item_commentAdapter madapter;



    @Override
    public void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        madapter  = new Circle_item_commentAdapter();
        madapter.openLoadAnimation(SCALEIN);
        madapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putInt("type",1);
                bundle.putString(FLASH_USER_ID, madapter.getItem(position).getUserId());

                UserInfoActivity.actionstart(CardActivity.this,bundle);
            }
        });
        recyclerView.setAdapter(madapter);

        loadComment();
    }

    @Override
    public void loadData() {
        httpGet(GET_CIRCLE_ITEM+"/create?circle_item_id="+circle_item_id,new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {

                Circle_Item item=new Gson().fromJson(result.toString(), Circle_Item.class);
                content.setText(item.getContent());
                type.setText(item.getTitle());
                time.setText(item.getCreateTime());
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

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_card);
        initToolbar("详细信息",true);
    }



    @Event(value = R.id.card_bt_comment ,type=View.OnClickListener.class)
    private void addComment(View view){
        String s=comment.getText().toString();
        MyPrefence prefence=MyPrefence.getInstance(CardActivity.this);
        Map<String, String> map= new HashMap<String,String> ();
        map.put("user_id",getUserId());
        map.put("content",s);
        map.put("circle_item_id",prefence.getInt(CIRCLE_ITEM_ID)+"");

        httpPost(POST_CIRCLE_ITEM_COMMENT, map, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                loadData();
                showSuccToast("评论成功");
                hideSoftInput(CardActivity.this);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("没有东西", ex.toString());

            }
            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }


    static public void actionStart(Context context){
        Intent intent=new Intent(context,CardActivity.class);
        context.startActivity(intent);
    }
    private  void loadComment(){
        LOG(POST_CIRCLE_ITEM_COMMENT+"/"+circle_item_id);
        httpGet(POST_CIRCLE_ITEM_COMMENT+"/"+circle_item_id, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LOG(result);
                circlelist.clear();
                switch (isJsonarray0(result)){
                    case 0:
                        circlelist = new Gson().fromJson(result, new TypeToken<ArrayList<Circle_Item_Comment>>() {
                        }.getType());
                        count.setText("评论("+circlelist.size()+")");
                        madapter.setNewData(circlelist);
                        break;
                    case 1:
                        Circle_Item_Comment item= new Gson().fromJson(result,Circle_Item_Comment.class);
                        count.setText("评论(1)");
                        madapter.addData(item);
                        break;
                    default:
                        Log.e("没有东西", "As");
                        break;
                }
                madapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("没有东西", ex+"");

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }





    public class Circle_item_commentAdapter  extends BaseQuickAdapter<Circle_Item_Comment, BaseViewHolder> {
        public Circle_item_commentAdapter() {

            super(R.layout.comment_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, Circle_Item_Comment item) {
            if(!RxDataUtils.isNullString(item.getImage())) {
                Picasso.with(CardActivity.this)
                        .load(item.getImage())
                        .resize(150, 150)
                        .centerCrop()
                        .into((CircleImageView) helper.getView(R.id.comment_item_image));
            }
            helper.setText(R.id.comment_item_content, item.getContent());
            helper.setText(R.id.comment_item_time, item.getTime())

                    .addOnClickListener(R.id.comment_item_image);
        }
    }
}
