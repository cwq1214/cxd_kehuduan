package com.cxd.khd.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cxd.khd.R;
import com.cxd.khd.api.BeanCallback;
import com.cxd.khd.api.Http;
import com.cxd.khd.entity.BaseJson;
import com.cxd.khd.entity.SKHomeMsgBoxResult;
import com.cxd.khd.util.Cache;
import com.cxd.khd.util.IntentHelper;
import com.cxd.khd.util.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/6/15.
 */

public class MessageBoxActivity extends BaseActivity {
    @BindView(R.id.img_sys)
    ImageView imgSys;
    @BindView(R.id.text_sysDate)
    TextView textSysDate;
    @BindView(R.id.text_sysmsg)
    TextView textSysmsg;
    @BindView(R.id.layout_sys)
    RelativeLayout layoutSys;
    @BindView(R.id.img_order)
    ImageView imgOrder;
    @BindView(R.id.text_orderDate)
    TextView textOrderDate;
    @BindView(R.id.text_ordermsg)
    TextView textOrdermsg;
    @BindView(R.id.layout_order)
    RelativeLayout layoutOrder;
    @BindView(R.id.text_sysCount)
    TextView textSysCount;
    @BindView(R.id.text_orderCount)
    TextView textOrderCount;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_box;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutOrder.setVisibility(View.GONE);
        layoutSys.setVisibility(View.GONE);
        if (Cache.getInstance().getLoginType()==1) {
            getSKMessage();
        }else if (Cache.getInstance().getLoginType() == 2){
            getBKMessage();
        }
    }

    private void getSKMessage(){
        Http.getSKHomeMessage(getContext(), new BeanCallback<BaseJson<SKHomeMsgBoxResult>>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson<SKHomeMsgBoxResult> response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(), response.forUser);
                    if (response.ret) {
                        if (response.data!=null&&response.data.order!=null){
                            textSysDate.setText(response.data.order.createdTime);
                            textSysmsg.setText(response.data.order.content);
                            layoutSys.setVisibility(View.VISIBLE);
                        }

                        if (response.data!=null&& !TextUtils.isEmpty(response.data.countOrder)){
                            MainActivity.msgCount = Integer.valueOf(response.data.countOrder);

                            if ("0".equals(response.data.countOrder)){
                                textSysCount.setVisibility(View.GONE);
                            }else {
                                textSysCount.setVisibility(View.VISIBLE);
                                if (Integer.valueOf(response.data.countOrder)>99){
                                    textSysCount.setText("99+");
                                }else {
                                    textSysCount.setText(response.data.countOrder);
                                }

                            }
                        }
                    }
                }else {
                    T.showShort(getContext(),e.getMessage());
                }
            }
        });
    }

    private void getBKMessage(){
        Http.getBKMessage(getContext(),new BeanCallback<BaseJson<SKHomeMsgBoxResult>>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson<SKHomeMsgBoxResult> response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(), response.forUser);
                    if (response.ret) {
                        if (response.data!=null&&response.data.order!=null){
                            textOrderDate.setText(response.data.order.createdTime);
                            textOrdermsg.setText(response.data.order.content);

                            layoutOrder.setVisibility(View.VISIBLE);
                        }
                        if (response.data!=null&& !TextUtils.isEmpty(response.data.countOrder)){
                            MainActivity.msgCount = Integer.valueOf(response.data.countOrder);

                            if ("0".equals(response.data.countOrder)){
                                textOrderCount.setVisibility(View.GONE);
                            }else {
                                textOrderCount.setVisibility(View.VISIBLE);
                                if (Integer.valueOf(response.data.countOrder)>99){
                                    textOrderCount.setText("99+");
                                }else {
                                    textOrderCount.setText(response.data.countOrder);
                                }

                            }
                        }
                    }
                }else {
                    T.showShort(getContext(),e.getMessage());
                }
            }
        });
    }

    @OnClick(R.id.layout_sys)
    public void onSysClick(){
        IntentHelper.openSystemMessageListActivity(getContext());
    }

    @OnClick(R.id.layout_order)
    public void onOrderClick(){
        IntentHelper.openOrderMessageListActivity(getContext());
    }
}
