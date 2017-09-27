package com.mage.magemata.publish.joingood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.user.UserInfoActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/5/30.
 */

public class JoinGoodCustomerActivity  extends BaseActivity {
    @ViewInject(R.id.base_rv)
    private RecyclerView mRecyclerView;
    private JoinGoodCustomerAdapter mFollowerAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_base);
        initToolbar("凑单信息",true);
    }

    @Override
    public void initData() {
        initRecyclerView();

    }

    @Override
    public void loadData() {

    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(JoinGoodCustomerActivity.this));

        mFollowerAdapter = new JoinGoodCustomerAdapter();

        mFollowerAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            }
            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                showConfirmDialog("确定要取消凑单吗",new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
            }
            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ContextCompat.getColor(JoinGoodCustomerActivity.this, R.color.yellow));
            }
        });
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mFollowerAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mFollowerAdapter.enableSwipeItem();


        mRecyclerView.setAdapter(mFollowerAdapter);

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, JoinGoodCustomerActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                CharSequence[] list={"金额信息","文字信息"};
                new MaterialDialog.Builder(this)
                        .title("需要发布的类型")
                        .items(list)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                switch (position){
                                    default:
                                        showInputDialog();
                                        break;
                                }
                            }
                        })
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private  void showInputDialog(){
//        new MaterialDialog.Builder(this)
//                .title("输入信息")
//                .inputType(
//                        InputType.TYPE_CLASS_TEXT
//                                | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE
//                                | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
//                .inputRange(1, 50)
//                .positiveText("提交")
//                .input("最长50字","",false,(dialog1,input) ->{
//                    JoinGoodCustomer status1 = new JoinGoodCustomer();
//                    status1.setUserName("只是一只卷");
//                    status1.setContent(input.toString());
//
//                    mFollowerAdapter.addData(status1);
//                })
//                .show();
    }

    class JoinGoodCustomerAdapter extends BaseItemDraggableAdapter<JoinGoodCustomer, BaseViewHolder> {

        public JoinGoodCustomerAdapter() {
            super(R.layout.rv_cv_joingoodcustomer, getSampleData(3));
        }

        @Override
        protected void convert(BaseViewHolder helper, JoinGoodCustomer item) {
//            helper.addOnClickListener(R.id.customer_item_portrait);
//            helper.setBackgroundRes(R.id.customer_item_portrait, R.mipmap.logo2);
            helper.setText(R.id.customer_item_name, item.getUserName());
            if (item.isNumber())
                helper.setText(R.id.customer_item_content, "发布了一个" + item.getContent() + "元的凑单");
            else
                helper.setText(R.id.customer_item_content, item.getContent());

        }

    }
    private List<JoinGoodCustomer> getSampleData(int lenth) {
        List<JoinGoodCustomer> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            JoinGoodCustomer status = new JoinGoodCustomer();
            status.setUserName("胖胖" + i);
            status.setCreatedAt("04/05/" + i);
            status.setContent("楼下品味不错");
            list.add(status);
        }
        return list;
    }
}
