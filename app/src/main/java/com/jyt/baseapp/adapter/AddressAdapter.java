package com.jyt.baseapp.adapter;

import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jyt.baseapp.view.viewholder.AddressItemViewHolder;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;

/**
 * Created by chenweiqi on 2017/6/16.
 */

public class AddressAdapter extends BaseRcvAdapter {

    BaseViewHolder.OnViewHolderClickListener onEditAddressClickListener ;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AddressItemViewHolder holder = new AddressItemViewHolder(parent);
        holder.setOnViewHolderClickListener(onViewHolderClickListener);
        holder.setOnEditAddressClick(onEditAddressClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
    }

    public void setOnEditAddressClickListener(BaseViewHolder.OnViewHolderClickListener onEditAddressClickListener) {
        this.onEditAddressClickListener = onEditAddressClickListener;
    }
}
