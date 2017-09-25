package com.mage.magemata.publish.delivery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 */

public class RiceConfirmActivity extends BaseActivity {
    @ViewInject(R.id.riceconfirm_linel_address)
    LinearLayout linel_address;
    @ViewInject(R.id.riceconfirm_linel_menu)
    LinearLayout linel_menu;
    @ViewInject(R.id.riceconfirm_listview)
    ListView listView;
    @ViewInject(R.id.riceconfirm_btn_submit)
    Button btn;



    @Override
    public void setContentView() {
        setContentView(R.layout.activity_riceconfirm);
        initToolbar("确认支付",true);
    }

    @Override
    public void initData() {

    }

    @Override
    public void loadData() {

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RiceConfirmActivity.class);
        context.startActivity(intent);
    }


    @Event(R.id.riceconfirm_linel_address)
    private void showAddress(View view) {
        final MaterialSimpleListAdapter adapter =
                new MaterialSimpleListAdapter(new MaterialSimpleListAdapter.Callback() {
                    @Override
                    public void onMaterialListItemSelected(MaterialDialog dialog, int index, MaterialSimpleListItem item) {
                        if(index==2){
                            new MaterialDialog.Builder(RiceConfirmActivity.this)
                                    .title("新增收货地址")
                                    .content("姓名")
                                    .inputType(
                                            InputType.TYPE_CLASS_TEXT
                                                    | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                                    | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                                    .inputRange(2, 16)
                                    .positiveText("确认提交")
                                    .input(
                                            "张三",
                                            null,
                                            false,
                                            new MaterialDialog.InputCallback() {
                                                @Override
                                                public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                                    dialog.dismiss();
                                                }
                                            }
                                    )
                                    .show();
                        }
                        Toast.makeText(RiceConfirmActivity.this,item.getContent().toString(),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                    }
                );
        adapter.add(
                new MaterialSimpleListItem.Builder(this)
                        .content("第一个收货地址")
                        .build());
        adapter.add(
                new MaterialSimpleListItem.Builder(this)
                        .content("第二个收货地址")
                        .build());
        adapter.add(
                new MaterialSimpleListItem.Builder(this)
                        .content(R.string.addaddress)
                        .icon(R.drawable.btn_add)
                        .iconPaddingDp(8)
                        .build());

        new MaterialDialog.Builder(this).title(R.string.addaddress).adapter(adapter, null).show();
    }
    @Event(R.id.riceconfirm_btn_submit)
    private void submit(View view) {
        new MaterialDialog.Builder(this)
                .content(R.string.addaddress)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
    }
}
