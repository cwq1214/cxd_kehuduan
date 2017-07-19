package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenweiqi on 2017/6/15.
 */

public class SKAddressListActivity extends BaseActivity {
    @BindView(R.id.view_recyclerView)
    RecyclerView viewRecyclerView;
    @BindView(R.id.view_refreshLayout)
    TwinklingRefreshLayout viewRefreshLayout;
    @BindView(R.id.btn_add)
    TextView btnAdd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sk_address_list;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
