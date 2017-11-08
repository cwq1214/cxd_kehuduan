package com.cxd.khd.view.viewholder;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxd.khd.R;
import com.cxd.khd.entity.BKOrderListResult;
import com.cxd.khd.entity.PackageDetailResult;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/6/21.
 */

public class BKOrderListItemViewHolder extends BaseViewHolder {

    @BindView(R.id.text_orderNo)
    TextView textOrderNo;
    @BindView(R.id.text_orderState)
    TextView textOrderState;


    String state;

    public BKOrderListItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order_item, parent, false));
    }

    public void setState(String state) {
        this.state = state;
        textOrderState.setText(state);

        if (state.contains("失败")){
            textOrderState.setTextColor(Color.RED);
        }else {
            textOrderState.setTextColor(itemView.getResources().getColor(R.color.colorPrimary));
        }

    }

    @Override
    public void setData(Object data, int position) {
        super.setData(data, position);
        if (data instanceof PackageDetailResult){
            textOrderNo.setText(((PackageDetailResult) data).packageNo);
        }else if (data instanceof BKOrderListResult){
            textOrderNo.setText(((BKOrderListResult) data).trackingNo);
        }
    }
}
