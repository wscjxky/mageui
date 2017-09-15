package com.mage.magemata.circle.card;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.user.MoreFragment;
import com.vondear.rxtools.RxImageUtils;
import com.vondear.rxtools.RxPhotoUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.body.FileBody;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static com.mage.magemata.constant.Constant.GET_CIRCLE_ITEM;
import static com.mage.magemata.constant.Constant.POST_CIRCLE_ITEM;
import static com.mage.magemata.constant.Constant.ROOT_URL;
import static com.mage.magemata.constant.Constant.UPLOAD;
import static com.mage.magemata.constant.Constant.VALUE_CIRCLE_ID;
import static com.mage.magemata.constant.Constant.VALUE_USER_ID;
import static com.mage.magemata.util.PublicMethod.httpPost;
import static com.vondear.rxtools.RxFileUtils.getFileAllSize;

/**
 * Created by Administrator on 2017/9/12.
 */

public class AddCircle_Item_Activity extends BaseActivity {
    private String url = POST_CIRCLE_ITEM;
    @ViewInject(R.id.addcard_et_title)
    EditText title;
    @ViewInject(R.id.addcard_et_content)
    EditText content;
    @ViewInject(R.id.addcard_iv_addimg)
    ImageView imageview;


    @Override
    public void initData() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_addcard);
        initToolbar("添加消息", true);
    }
    @Event(R.id.addcard_iv_addimg)
    private void addImage(View view){
        RxPhotoUtils.openLocalImage(this);

    }

    @Event(R.id.addcard_btn_submit)
    private void submit(View view) {
        if (TextUtils.isEmpty(title.getText().toString()) || TextUtils.isEmpty(content.getText().toString())) {
            showErrorToast("不可为空哦");
        } else {
            showLoadingDialog();
            Map<String, String> map = new HashMap<String, String>();
            map.put("circle_id", VALUE_CIRCLE_ID);
            map.put("title", title.getText().toString());
            map.put("content", content.getText().toString());
            map.put("type", "文字");
            map.put("user_id", VALUE_USER_ID);
            httpPost(url, map, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    showSuccToast("成功啦");
                    finish();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    showSuccToast("失败了");

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    cancelLoadingDialog();

                }

            });
        }
    }

    public static void actionStart(Context  context, String s)
    {
        Intent intent=new Intent(context,AddCircle_Item_Activity.class);
        context.startActivity(intent);
    }

}
