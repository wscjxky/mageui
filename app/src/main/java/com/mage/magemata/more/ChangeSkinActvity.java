package com.mage.magemata.more;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mage.magemata.R;
import com.mage.magemata.circle.Circle;
import com.mage.magemata.circle.CircleFragment;
import com.mage.magemata.circle.card.CircleActivity;
import com.mage.magemata.main.BaseActivity;
import com.mage.magemata.main.MainActivity;
import com.mage.magemata.user.ChangeUserActivity;
import com.squareup.picasso.Picasso;
import com.vondear.rxtools.RxImageUtils;
import com.vondear.rxtools.RxPhotoUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;
import static com.mage.magemata.constant.Constant.ITEM_LOGO_SIZE;
import static com.mage.magemata.constant.Constant.SET_BACK_GROUND;
import static com.mage.magemata.util.PublicMethod.LOG;


/**
 * Created by Administrator on 2017/10/12.
 */

public class ChangeSkinActvity extends BaseActivity {
    @ViewInject(R.id.changeskin_recycler_view)
    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private RelativeLayout backgournd;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_changeskin);
        setStatus(recyclerView);

    }

    @Override
    public void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        photoAdapter  = new PhotoAdapter();
        photoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SharedPreferences skinSettingPreference=ChangeSkinActvity.this.getSharedPreferences("background", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = skinSettingPreference.edit();
                String url=getUriFromDrawableRes(photoAdapter.getItem(position).image);
                editor.putString("background",url);
                editor.apply();;
                LOG( url);
                finish();
            }
        });
        recyclerView.setAdapter(photoAdapter);
    }


    @Override
    public void loadData() {
        LayoutInflater factory = LayoutInflater.from(ChangeSkinActvity.this);
        View layout = factory.inflate(R.layout.activity_main, null);
        backgournd=(RelativeLayout) layout.findViewById(R.id.main_background) ;
    }
    @Event(R.id.changskin_btn_openimage)
    private void openimage(View view){
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,SET_BACK_GROUND);
    }

    public class PhotoAdapter extends BaseQuickAdapter<MyPhoto, BaseViewHolder> {
        PhotoAdapter() {
            super(R.layout.photo_item,createDummyDataList());
        }

        @Override
        public void convert(BaseViewHolder helper, MyPhoto item) {
            Picasso.with(ChangeSkinActvity.this)
                    .load(item.image)
                    .into( (ImageView)helper.getView(R.id.background));
        }
    }
    public class MyPhoto {
        public int image;
    }

    private List<MyPhoto> createDummyDataList() {
        List<MyPhoto> list=new ArrayList<>();
        MyPhoto photo=new MyPhoto();
        photo.image=R.drawable.back4;
        MyPhoto photo1=new MyPhoto();
        photo1.image=R.drawable.back3;
        MyPhoto photo2=new MyPhoto();
        photo2.image=R.drawable.back5;
        list.add(photo);
        list.add(photo1);
        list.add(photo2);
        return list;
    }
    public void setBack(String url){
        SharedPreferences skinSettingPreference=this.getSharedPreferences("background", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = skinSettingPreference.edit();
        editor.putString("background", url);
        editor.apply();
        Bitmap bitmap = RxImageUtils.getBitmap(url);
        Drawable drawable=RxImageUtils.bitmap2Drawable(bitmap);
        Log.e("sd",url);
        backgournd.setBackground(drawable);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SET_BACK_GROUND://选择相册之后的处理
                Log.e("sdf","Sdf");
                if (resultCode == RESULT_OK) {
                    String url= RxPhotoUtils.getRealFilePath(this,data.getData());
                    setBack(url);
                    finish();
                }
                break;
            case RxPhotoUtils.CROP_IMAGE://普通裁剪后的处理
                Log.e("asd",data.getData()+"");
//               RequestUpdateAvatar(new File(RxPhotoUtils.getRealFilePath(activity, RxPhotoUtils.cropImageUri)));
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getUriFromDrawableRes(int id) {
        Resources resources = this.getResources();
        String path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + resources.getResourcePackageName(id) + "/"
                + resources.getResourceTypeName(id) + "/"
                + resources.getResourceEntryName(id);
        return path;
    }

}
