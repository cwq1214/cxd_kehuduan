package com.cxd.khd.adapter;

import android.view.ViewGroup;

import com.cxd.khd.view.viewholder.BKCreateOrderItemViewHolder;
import com.cxd.khd.view.viewholder.BaseViewHolder;

/**
 * Created by chenweiqi on 2017/6/20.
 */

public class BKCreateOrderListAdapter extends BaseRcvAdapter {

    BaseViewHolder.OnViewHolderClickListener onDelImageClickListener;


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BKCreateOrderItemViewHolder holder = new BKCreateOrderItemViewHolder(parent);
        holder.setOnDelImageClickListener(onDelImageClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
    }


    public void setOnDelImageClickListener(BaseViewHolder.OnViewHolderClickListener onDelImageClickListener) {
        this.onDelImageClickListener = onDelImageClickListener;
    }
}
