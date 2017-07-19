package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.viewholder.SKOrderListItemViewHolder;

/**
 * Created by chenweiqi on 2017/6/19.
 */

public class SKOrderListAdapter extends BaseRcvAdapter {
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SKOrderListItemViewHolder holder = new SKOrderListItemViewHolder(parent);
        holder.setOnViewHolderClickListener(onViewHolderClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
    }
}
