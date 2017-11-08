package com.cxd.khd.adapter;

import android.view.ViewGroup;

import com.cxd.khd.view.viewholder.BaseViewHolder;
import com.cxd.khd.view.viewholder.MsgItemViewHolder;

/**
 * Created by chenweiqi on 2017/6/13.
 */

public class MessageListAdapter extends BaseRcvAdapter{
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MsgItemViewHolder holder = new MsgItemViewHolder(parent);
        holder.setOnViewHolderClickListener(onViewHolderClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
            holder.setData(dataList.get(position),position);
    }

}
