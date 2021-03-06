package com.cxd.khd.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cxd.khd.R;
import com.cxd.khd.adapter.SKOrderListAdapter;
import com.cxd.khd.api.BeanCallback;
import com.cxd.khd.api.Http;
import com.cxd.khd.entity.BaseJson;
import com.cxd.khd.entity.Order;
import com.cxd.khd.util.IntentHelper;
import com.cxd.khd.util.T;
import com.cxd.khd.view.viewholder.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/6/15.
 */

public class SKOrderListFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;


    public static int TYPE_DOING = 1;
    public static int TYPE_FINISH = 2;
    public static int TYPE_CANCEL = 3;

    public int type = -1;
    SKOrderListAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.layout_refreshview_and_recyclerview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerview.setAdapter(adapter = new SKOrderListAdapter());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_rcv));
        recyclerview.addItemDecoration(itemDecoration);
        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder, Object data, int position) {
                IntentHelper.openSKOrderDetailActivity(getContext(), ((Order) data).orderId);
            }
        });

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                getSKOrderList();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refreshLayout.finishLoadmore();
            }
        });


        refreshLayout.startRefresh();
    }

    private void getSKOrderList(){
        Http.getSKOrderList(getContext(), type, new BeanCallback<BaseJson<List<Order>>>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson<List<Order>> response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(),response.forUser);
                    if (response.ret){
                        adapter.setDataList(response.data);
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    T.showShort(getContext(),e.getMessage());
                }

                refreshLayout.finishRefreshing();
            }
        });
    }
}
