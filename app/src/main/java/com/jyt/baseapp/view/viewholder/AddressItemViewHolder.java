package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.entity.Address;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/6/16.
 */

public class AddressItemViewHolder extends BaseViewHolder {


    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_phone)
    TextView textPhone;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.img_edit)
    ImageView imgEdit;

    BaseViewHolder.OnViewHolderClickListener onEditAddressClick;

    public AddressItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_address_item, parent, false));

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditAddressClick!=null)
                    onEditAddressClick.onClick(AddressItemViewHolder.this,data,position);
            }
        });
    }

    public void setOnEditAddressClick(OnViewHolderClickListener onEditAddressClick) {
        this.onEditAddressClick = onEditAddressClick;
    }

    @Override
    public void setData(Object data, int position) {
        super.setData(data, position);
        Address address = (Address) data;

        textName.setText(address.name);
        textPhone.setText(address.mobile);
        textAddress.setText(address.address);

    }
}
