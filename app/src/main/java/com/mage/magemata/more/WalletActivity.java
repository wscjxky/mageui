package com.mage.magemata.more;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.mage.magemata.R;
import com.mage.magemata.main.BaseActivity;


/**
 * Created by Administrator on 2017/5/19.
 */

public class WalletActivity extends BaseActivity {

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_wallet);
        initToolbar("我的钱包",true);

    }

    @Override
    public void initData() {

    }

    @Override
    public void loadData() {

    }


}
