package com.cxd.khd.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cxd.khd.R;
import com.cxd.khd.util.IntentHelper;
import com.cxd.khd.view.fragment.BKOrderListFragment;

/**
 * Created by chenweiqi on 2017/6/22.
 */

public class BKPackOrderActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String packageNo = null;
        if (intent != null){
            packageNo = intent.getStringExtra(IntentHelper.KEY_PACKAGE_ID);
        }

        BKOrderListFragment bkOrderListFragment = new BKOrderListFragment();
        bkOrderListFragment.packageNo = packageNo;
        getSupportFragmentManager().beginTransaction().replace(R.id.content,bkOrderListFragment).commitAllowingStateLoss();
    }
}
