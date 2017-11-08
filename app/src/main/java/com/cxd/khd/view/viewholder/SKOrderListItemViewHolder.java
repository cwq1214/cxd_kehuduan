package com.cxd.khd.view.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxd.khd.R;
import com.cxd.khd.entity.Order;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/6/19.
 */

public class SKOrderListItemViewHolder extends BaseViewHolder {
    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.text_state)
    TextView textState;
    @BindView(R.id.text_toPerson)
    TextView textToPerson;
    @BindView(R.id.text_toAddress)
    TextView textToAddress;

    public SKOrderListItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_sk_order_list_item, parent, false));
    }

    @Override
    public void setData(Object data, int position) {
        super.setData(data, position);
        Order order = (Order) data;

        textDate.setText(order.createdTime);
        textState.setText(order.orderState);
        textToPerson.setText(order.userMsg);


    }
}
