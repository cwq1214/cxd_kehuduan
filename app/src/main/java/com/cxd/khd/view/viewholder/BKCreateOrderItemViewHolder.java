package com.cxd.khd.view.viewholder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxd.khd.R;
import com.cxd.khd.entity.BKOrderListItem;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/6/20.
 */

public class BKCreateOrderItemViewHolder extends BaseViewHolder {
    @BindView(R.id.text_orderNo)
    TextView textOrderNo;
    @BindView(R.id.text_value)
    EditText textValue;
    @BindView(R.id.img_del)
    ImageView imgDel;

    BaseViewHolder.OnViewHolderClickListener onDelImageClickListener;

    public BKCreateOrderItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bk_batch_order_item, parent, false));

        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onDelImageClickListener!=null){
                    onDelImageClickListener.onClick(BKCreateOrderItemViewHolder.this,data,position);
                }
            }
        });

        textValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((BKOrderListItem) data).value = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void setData(Object data, int position) {
        super.setData(data, position);
        BKOrderListItem item = (BKOrderListItem) data;
        textOrderNo.setText(item.orderNo);

        textValue.setText(item.value);
    }


    public void setOnDelImageClickListener(OnViewHolderClickListener onDelImageClickListener) {
        this.onDelImageClickListener = onDelImageClickListener;
    }
}
