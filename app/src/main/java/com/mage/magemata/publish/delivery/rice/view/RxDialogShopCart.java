package com.mage.magemata.publish.delivery.rice.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mage.magemata.R;
import com.mage.magemata.publish.delivery.RiceConfirmActivity;
import com.mage.magemata.publish.delivery.rice.adapter.AdapterPopupDish;
import com.mage.magemata.publish.delivery.rice.adapter.ShopCartInterface;
import com.mage.magemata.publish.delivery.rice.bean.ModelShopCart;

import static com.mage.magemata.util.PublicMethod.LOG;


/**
 * Created by cheng on 16-12-22.
 */
public class RxDialogShopCart extends Dialog implements View.OnClickListener,ShopCartInterface {

    private LinearLayout linearLayout,bottomLayout,clearLayout;
    private FrameLayout shopingcartLayout;
    private ModelShopCart mModelShopCart;
    private TextView totalPriceTextView;
    private TextView totalPriceNumTextView;
    private RecyclerView recyclerView;
    private AdapterPopupDish dishAdapter;
    private ShopCartDialogImp shopCartDialogImp;
    private Button submitbutton;

    public RxDialogShopCart(Context context, ModelShopCart modelShopCart, int themeResId) {
        super(context,themeResId);
        this.mModelShopCart = modelShopCart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_popupview);

        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        clearLayout = (LinearLayout)findViewById(R.id.clear_layout);
        shopingcartLayout = (FrameLayout)findViewById(R.id.shopping_cart_layout);
        bottomLayout = (LinearLayout)findViewById(R.id.shopping_cart_bottom);
        totalPriceTextView = (TextView)findViewById(R.id.shopping_cart_total_tv);
        totalPriceNumTextView = (TextView)findViewById(R.id.shopping_cart_total_num);
        recyclerView = (RecyclerView)findViewById(R.id.recycleview);
        submitbutton=(Button)findViewById(R.id.shopping_cart_btn_submit);
        submitbutton.setOnClickListener(this);
        shopingcartLayout.setOnClickListener(this);
        bottomLayout.setOnClickListener(this);
        clearLayout.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dishAdapter = new AdapterPopupDish(getContext(), mModelShopCart);
        recyclerView.setAdapter(dishAdapter);
        dishAdapter.setShopCartInterface(this);
        showTotalPrice();
    }

    @Override
    public void show() {
        super.show();
        animationShow(500);
    }

    @Override
    public void dismiss() {
        animationHide(500);
    }

    private void showTotalPrice(){
        if(mModelShopCart !=null && mModelShopCart.getShoppingTotalPrice()>0){
            totalPriceTextView.setVisibility(View.VISIBLE);
            totalPriceTextView.setText("¥ "+ mModelShopCart.getShoppingTotalPrice());
            totalPriceNumTextView.setVisibility(View.VISIBLE);
            totalPriceNumTextView.setText(""+ mModelShopCart.getShoppingAccount());

        }else {
            totalPriceTextView.setVisibility(View.GONE);
            totalPriceNumTextView.setVisibility(View.GONE);
        }
    }

    private void animationShow(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearLayout, "translationY",1000, 0).setDuration(mDuration)
        );
        animatorSet.start();
    }

    private void animationHide(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearLayout, "translationY",0,1000).setDuration(mDuration)
        );
        animatorSet.start();

        if(shopCartDialogImp!=null){
            shopCartDialogImp.dialogDismiss();
        }

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                RxDialogShopCart.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shopping_cart_btn_submit:
                LOG("as");
                RiceConfirmActivity.actionStart(getContext());
                break;
            case R.id.shopping_cart_bottom:
            case R.id.shopping_cart_layout:
                this.dismiss();
                break;
            case R.id.clear_layout:
                clear();
                break;
        }
    }

    @Override
    public void add(View view, int postion) {
        showTotalPrice();
    }

    @Override
    public void remove(View view, int postion) {
        showTotalPrice();
        if(mModelShopCart.getShoppingAccount()==0){
            this.dismiss();
        }
    }

    public ShopCartDialogImp getShopCartDialogImp() {
        return shopCartDialogImp;
    }

    public void setShopCartDialogImp(ShopCartDialogImp shopCartDialogImp) {
        this.shopCartDialogImp = shopCartDialogImp;
    }

    public interface ShopCartDialogImp{
        public void dialogDismiss();
    }

    public void clear(){
        mModelShopCart.clear();
        showTotalPrice();
        if(mModelShopCart.getShoppingAccount()==0){
            this.dismiss();
        }
    }
}
