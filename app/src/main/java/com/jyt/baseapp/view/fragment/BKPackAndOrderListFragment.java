package com.jyt.baseapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.BKOrderListAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Http;
import com.jyt.baseapp.entity.BKOrderListResult;
import com.jyt.baseapp.entity.BaseJson;
import com.jyt.baseapp.entity.PackageDetailResult;
import com.jyt.baseapp.util.IntentHelper;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/6/21.
 */

public class BKPackAndOrderListFragment extends BaseFragment {

    public static final int PACKAGE_DOING = 0;
    public static final int PACKAGE_FINISH = 1;
    public static final int ORDER_READY = 2;
    public static final int ORDER_DOING = 3;
    public static final int ORDER_FINISH = 4;
    public static final int ORDER_FAILED = 5;

    public int type = -1;

    public String packageNo;

    String title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;


    BKOrderListAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pack_order_list;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_rcv));
        recyclerview.addItemDecoration(itemDecoration);
        recyclerview.setAdapter(adapter = new BKOrderListAdapter());
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refreshLayout.finishLoadmore();
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                getList();

            }
        });
        adapter.setState(title);
        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder, Object data, int position) {
                if (data instanceof PackageDetailResult){
                    IntentHelper.openPackDetailActivity(getContext(),((PackageDetailResult) data).packageId);
                }else if (data instanceof BKOrderListResult){
                    IntentHelper.openBKOrderDetailActivity(getContext(),((BKOrderListResult) data).orderId,title);
                }
            }
        });
        refreshLayout.startRefresh();

    }

    private void getList() {
        if (type < 0) {
            return;
        }

        if (type < 2) {
            Http.getBKPackageOrderList(getContext(), type + 1 + "", new BeanCallback<BaseJson<List<PackageDetailResult>>>(getContext()) {
                @Override
                public void onResult(boolean success, BaseJson<List<PackageDetailResult>> response, Exception e) {
                    super.onResult(success, response, e);
                    if (success) {
                        T.showShort(getContext(), response.forUser);
                        if (response.ret) {
                            adapter.setDataList(response.data);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        T.showShort(getContext(), e.getMessage());
                    }
                    refreshLayout.finishRefreshing();
                    refreshLayout.finishLoadmore();
                }
            });

            return;
        }
        if (type > 1 && type <= 5) {
            Http.getBKOrderList(getContext(), type - 1 + "", packageNo, new BeanCallback<BaseJson<List<BKOrderListResult>>>(getContext()) {
                @Override
                public void onResult(boolean success, BaseJson<List<BKOrderListResult>> response, Exception e) {
                    super.onResult(success, response, e);
                    if (success) {
                        T.showShort(getContext(), response.forUser);
                        if (response.ret) {
                            adapter.setDataList(response.data);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        T.showShort(getContext(), e.getMessage());
                    }
                    refreshLayout.finishRefreshing();
                    refreshLayout.finishLoadmore();
                }
            });
        }
    }
}
