package com.cxd.khd.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cxd.khd.R;
import com.cxd.khd.adapter.ExpressCompanyAdapter;
import com.cxd.khd.entity.ExpressCompany;
import com.cxd.khd.util.IntentHelper;
import com.cxd.khd.view.viewholder.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/6/16.
 */

public class ExpressCompanyListActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;


    ExpressCompanyAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.layout_refreshview_and_recyclerview;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<ExpressCompany> expressCompanies = getIntent().getParcelableArrayListExtra(IntentHelper.KEY_COMPANIES);

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_rcv));
        recyclerview.addItemDecoration(itemDecoration);
        recyclerview.setAdapter(adapter = new ExpressCompanyAdapter());
        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder, Object data, int position) {
                Intent intent = new Intent();
                intent.putExtra(IntentHelper.KEY_COMPANY, (Parcelable) data);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        adapter.setDataList(expressCompanies);
        adapter.notifyDataSetChanged();

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refreshLayout.finishLoadmore();
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                refreshLayout.finishRefreshing();
            }
        });



    }


}
