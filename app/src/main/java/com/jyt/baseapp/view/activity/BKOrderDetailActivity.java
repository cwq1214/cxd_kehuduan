package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Http;
import com.jyt.baseapp.entity.BKOrderDetailResult;
import com.jyt.baseapp.entity.BaseJson;
import com.jyt.baseapp.util.IntentHelper;
import com.jyt.baseapp.util.T;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/6/22.
 */

public class BKOrderDetailActivity extends BaseActivity {

    @BindView(R.id.text_orderNo)
    TextView textOrderNo;
    @BindView(R.id.text_trackingNo)
    TextView textTrackingNo;
    @BindView(R.id.layout_hezuoren)
    LinearLayout layoutHezuoren;
    @BindView(R.id.text_state)
    TextView textState;
    @BindView(R.id.text_signPerson)
    TextView textSignPerson;
    @BindView(R.id.layout_receive)
    RelativeLayout layoutReceive;
    @BindView(R.id.text_failedReasno)
    TextView textFailedReasno;
    @BindView(R.id.layout_failed)
    RelativeLayout layoutFailed;
    @BindView(R.id.layout_progress)
    LinearLayout layoutProgress;

    String orderId;
    String state;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bk_order_detail;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            state = intent.getStringExtra(IntentHelper.KEY_STATE);
            orderId = intent.getStringExtra(IntentHelper.KEY_ORDER_ID);
        }

        getOrderDetail(orderId);
    }

    private void getOrderDetail(String orderId) {
        Http.getBKOrderDetail(getContext(), orderId, new BeanCallback<BaseJson<BKOrderDetailResult>>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson<BKOrderDetailResult> response, Exception e) {
                super.onResult(success, response, e);
                if (success) {
                    T.showShort(getContext(), response.forUser);
                    if (response.ret) {
                        initView(response.data);
                    }
                } else {
                    T.showShort(getContext(), e.getMessage());
                }
            }
        });
    }


    private void initView(BKOrderDetailResult result) {
        textOrderNo.setText(result.orderNo);
        textTitle.setText("订单详情");
        textState.setText(state);
        textTrackingNo.setText(result.trackingNo);
        if (state.contains("失败")){
            textState.setTextColor(Color.RED);
        }

        if (TextUtils.isEmpty(result.signer)) {
            layoutReceive.setVisibility(View.GONE);
        }else{
            textSignPerson.setText(result.signer);
        }

        if (TextUtils.isEmpty(result.failureReason)){
            layoutFailed.setVisibility(View.GONE);
        }else {
            textFailedReasno.setText(result.failureReason);
        }

        if (!TextUtils.isEmpty(result.partnerName)||!TextUtils.isEmpty(result.mobile)){
            layoutHezuoren.removeAllViews();

            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_hehuoren,layoutHezuoren,false);

            TextView textName = (TextView) view.findViewById(R.id.text_name);

            TextView textPhone = (TextView) view.findViewById(R.id.text_phone);

            textName.setText(result.partnerName);

            textPhone.setText(result.mobile);

            layoutHezuoren.addView(view);
        }

        if (result.track!=null)
        for (int i=0;i<result.track.size();i++){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_order_progress,layoutProgress,false);
            TextView textTitle = (TextView) view.findViewById(R.id.text_title);
            TextView textDate = (TextView) view.findViewById(R.id.text_date);

            textTitle.setText(result.track.get(i).msg);
            textDate.setText(result.track.get(i).msgTime);

            if (i+1==result.track.size()) {
                view.findViewById(R.id.view_line).setVisibility(View.GONE);
            }
            layoutProgress.addView(view);
        }

    }
}

