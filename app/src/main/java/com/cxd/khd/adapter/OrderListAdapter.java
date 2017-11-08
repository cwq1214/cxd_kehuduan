package com.cxd.khd.adapter;

import android.view.ViewGroup;

import com.cxd.khd.view.viewholder.BaseViewHolder;
import com.cxd.khd.view.viewholder.OrderListItemViewHolder;

/**
 * Created by chenweiqi on 2017/6/1.
 */

public class OrderListAdapter extends BaseRcvAdapter {


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderListItemViewHolder orderListItemViewHolder = new OrderListItemViewHolder(parent);
        orderListItemViewHolder.setOnViewHolderClickListener(onViewHolderClickListener);
        return orderListItemViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
         holder.setData(dataList.get(position),position);
    }


}
