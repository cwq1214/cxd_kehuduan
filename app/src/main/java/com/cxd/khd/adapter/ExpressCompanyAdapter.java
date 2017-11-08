package com.cxd.khd.adapter;

import android.view.ViewGroup;

import com.cxd.khd.view.viewholder.BaseViewHolder;
import com.cxd.khd.view.viewholder.ExpressCompanyViewHolder;

/**
 * Created by chenweiqi on 2017/6/19.
 */

public class ExpressCompanyAdapter extends BaseRcvAdapter {
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ExpressCompanyViewHolder viewHolder = new ExpressCompanyViewHolder(parent);

        viewHolder.setOnViewHolderClickListener(onViewHolderClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
    }
}
