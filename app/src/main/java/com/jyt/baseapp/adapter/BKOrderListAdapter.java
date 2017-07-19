package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BKOrderListItemViewHolder;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;

/**
 * Created by chenweiqi on 2017/6/21.
 */

public class BKOrderListAdapter extends BaseRcvAdapter {

    String state;
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BKOrderListItemViewHolder holder = new BKOrderListItemViewHolder(parent);
        holder.setOnViewHolderClickListener(onViewHolderClickListener);
        holder.setState(state);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
    }

    public void setState(String state) {
        this.state = state;
    }
}
