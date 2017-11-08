package com.cxd.khd.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cxd.khd.R;
import com.cxd.khd.adapter.AddressAdapter;
import com.cxd.khd.api.BeanCallback;
import com.cxd.khd.api.Http;
import com.cxd.khd.entity.Address;
import com.cxd.khd.entity.BaseJson;
import com.cxd.khd.util.IntentHelper;
import com.cxd.khd.util.T;
import com.cxd.khd.view.viewholder.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/6/16.
 */

public class AddressListActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.text_newAddress)
    TextView textNewAddress;


    AddressAdapter addressAdapter;
    boolean noClose = false;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noClose = getIntent().getBooleanExtra(IntentHelper.KEY_NO_CLOSE,false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_rcv));
        recyclerview.addItemDecoration(itemDecoration);
        recyclerview.setAdapter(addressAdapter = new AddressAdapter());
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        addressAdapter.setOnEditAddressClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder, Object data, int position) {
                IntentHelper.openEditAddressActivity(getContext(), (Address) data);
            }
        });
        addressAdapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder, Object data, int position) {
                if (noClose){
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(IntentHelper.KEY_ADDRESS, (Parcelable) data);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refreshLayout.finishLoadmore();
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);

                getAddressList();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLayout.startRefresh();

    }

    private void getAddressList(){
        Http.getSKAddressList(getContext(), new BeanCallback<BaseJson<List<Address>>>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson<List<Address>> response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    if (response.ret){
                        addressAdapter.setDataList(response.data);
                        addressAdapter.notifyDataSetChanged();
                    }
                    T.showShort(getContext(),response.forUser);
                }else {
                    T.showShort(getContext(),e.getMessage());
                }

                refreshLayout.finishRefreshing();
            }
        });
    }

    @OnClick(R.id.text_newAddress)
    public void onNewAddressClick(){
        IntentHelper.openAddNewAddressActivity(getContext());
    }
}
