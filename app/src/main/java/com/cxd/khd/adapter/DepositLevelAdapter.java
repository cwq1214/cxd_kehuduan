package com.cxd.khd.adapter;

import android.view.ViewGroup;

import com.cxd.khd.view.viewholder.BaseViewHolder;
import com.cxd.khd.view.viewholder.DepositLevelItemViewHolder;

/**
 * Created by chenweiqi on 2017/6/2.
 */

public class DepositLevelAdapter extends BaseRcvAdapter {

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DepositLevelItemViewHolder depositLevelItemViewHolder = new DepositLevelItemViewHolder(parent);
        depositLevelItemViewHolder.setOnViewHolderClickListener(onViewHolderClickListener);
        return depositLevelItemViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
    }


}
