package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.entity.ExpressCompany;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/6/19.
 */

public class ExpressCompanyViewHolder extends BaseViewHolder {
    @BindView(R.id.text_companyName)
    TextView textCompanyName;
    @BindView(R.id.text_discount)
    TextView textDiscount;

    public ExpressCompanyViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_express_company, parent, false));

    }


    @Override
    public void setData(Object data, int position) {
        super.setData(data, position);
        ExpressCompany expressCompany = (ExpressCompany) data;
        textCompanyName.setText(expressCompany.eName);
        textDiscount.setText(String.format("补贴%s元",expressCompany.subsidy));


    }
}
