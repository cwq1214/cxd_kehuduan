package com.cxd.khd.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cxd.khd.R;
import com.cxd.khd.entity.ExpressCompany;
import com.cxd.khd.util.DensityUtil;
import com.cxd.khd.util.IntentHelper;
import com.cxd.khd.util.ScreenUtils;
import com.cxd.khd.util.SoftInputUtil;
import com.cxd.khd.util.T;
import com.cxd.khd.view.widget.RadioGroupEx;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/6/5.
 */

public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.text_company)
    TextView textCompany;
    @BindView(R.id.text_payType)
    TextView textPayType;
    @BindView(R.id.rbtn_online)
    RadioButton rbtnOnline;
    @BindView(R.id.rbtn_offline)
    RadioButton rbtnOffline;
    @BindView(R.id.rbtn_cash)
    RadioButton rbtnCash;
    @BindView(R.id.group_payType)
    RadioGroup groupPayType;
    @BindView(R.id.text_keepValue)
    TextView textKeepValue;
    @BindView(R.id.rbtn_notKeepValue)
    RadioButton rbtnNotKeepValue;
    @BindView(R.id.rbtn_keepValue)
    RadioButton rbtnKeepValue;
    @BindView(R.id.group_keepValue)
    RadioGroup groupKeepValue;
    @BindView(R.id.input_cost)
    EditText inputCost;
    @BindView(R.id.group_company)
    RadioGroupEx groupCompany;
    @BindView(R.id.layout_moreCompany)
    RelativeLayout layoutMoreCompany;

    String keepValue;
    String payType;
    String cost;
    String company;

    List<ExpressCompany> companies;
    ExpressCompany selCompany;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnFunction.setText("确定");

        groupKeepValue.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String text = ((RadioButton) group.findViewById(checkedId)).getText().toString();
                textKeepValue.setText(text);
            }
        });
        groupPayType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String text = ((RadioButton) group.findViewById(checkedId)).getText().toString();
                textPayType.setText(text);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            payType = intent.getStringExtra(IntentHelper.KEY_PAY_TYPE);
            keepValue = intent.getStringExtra(IntentHelper.KEY_KEEP_VALUE);
            cost = intent.getStringExtra(IntentHelper.KEY_COST);
            selCompany = intent.getParcelableExtra(IntentHelper.KEY_COMPANY);
            companies = intent.getParcelableArrayListExtra(IntentHelper.KEY_COMPANIES);
            buildButton(companies);

            if (selCompany!=null)
            for (int i=0;i<groupCompany.getChildCount();i++){
                if (((RadioButton) groupCompany.getChildAt(i)).getText().toString().equals(selCompany.eName)){
                    ((RadioButton) groupCompany.getChildAt(i)).setChecked(true);
                    break;
                }
            }

            for (int i=0;i<groupKeepValue.getChildCount();i++){
                if (((RadioButton) groupKeepValue.getChildAt(i)).getText().toString().equals(keepValue)){
                    ((RadioButton) groupKeepValue.getChildAt(i)).setChecked(true);
                    break;
                }
            }
            for (int i=0;i<groupPayType.getChildCount();i++){
                if (((RadioButton) groupPayType.getChildAt(i)).getText().toString().equals(payType)){
                    ((RadioButton) groupPayType.getChildAt(i)).setChecked(true);
                    break;
                }
            }
        }




        groupCompany.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View view = group.findViewById(checkedId);
                if (view!=null) {
                    selCompany = (ExpressCompany) view.getTag();
                    textCompany.setText(selCompany.eName);
                }
            }
        });
        textCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                company = s.toString();
            }
        });


        textKeepValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keepValue = s.toString();
            }
        });
        textPayType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                payType = s.toString();
            }
        });
        inputCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cost = s.toString();
            }
        });



        textKeepValue.setText(keepValue);
        textPayType.setText(payType);
        if (selCompany!=null)
        textCompany.setText(selCompany.eName);
        inputCost.setText(cost);

        inputCost.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    inputCost.setText("");
                    SoftInputUtil.showSoftKeyboard(getContext(),inputCost);
                }
            }
        });
    }


    @Override
    public void onFunctionClick() {
        super.onFunctionClick();
        if (selCompany==null){
            T.showShort(getContext(),"请选择快递公司");
            return;
        }
        if (TextUtils.isEmpty(payType)){
            T.showShort(getContext(),"请选择支付方式");
            return;
        }
        if (TextUtils.isEmpty(keepValue)){
            T.showShort(getContext(),"请选择是否保价");
            return;
        }


        setResultAndFinish();
    }

    private void setResultAndFinish() {
        Intent intent = new Intent();
        intent.putExtra(IntentHelper.KEY_COMPANY, selCompany);
        intent.putExtra(IntentHelper.KEY_PAY_TYPE, payType);
        intent.putExtra(IntentHelper.KEY_KEEP_VALUE, keepValue);
//        intent.putExtra(IntentHelper.KEY_COST, cost);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.layout_moreCompany)
    public void onMoreCompanyClick(){
        IntentHelper.openExpressCompanyListActivityForResult(getContext(),companies);
    }

    private void buildButton(List<ExpressCompany> msg) {
        int dp_10 = DensityUtil.dpToPx(getContext(), 10);
        int width = (ScreenUtils.getScreenWidth(getContext()) - DensityUtil.dpToPx(getContext(), 80)) / 3;
        RadioGroupEx groupEx;
        String formatText;
        groupEx = groupCompany;
        formatText = "%s";


        for (int i = 0, max = Math.min(msg.size(),6); i < max; i++) {

            RadioButton radioButton = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.layout_radiobutton, groupEx, false);

            RadioGroupEx.LayoutParams params = new RadioGroupEx.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(dp_10, dp_10, dp_10, dp_10);
            radioButton.setLayoutParams(params);

            radioButton.setTag(msg.get(i));
            radioButton.setText(Html.fromHtml(String.format(formatText, msg.get(i).eName)));

            groupEx.addView(radioButton);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IntentHelper.REQUIRE_CODE_EDIT_ORDER_PAY_INFO
                &&resultCode==RESULT_OK){
            groupCompany.clearCheck();
            selCompany = data.getParcelableExtra(IntentHelper.KEY_COMPANY);
            textCompany.setText(selCompany.eName);
        }
    }
}
