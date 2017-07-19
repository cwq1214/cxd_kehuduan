package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Http;
import com.jyt.baseapp.entity.BaseJson;
import com.jyt.baseapp.entity.HomeMsgBox;
import com.jyt.baseapp.entity.SKHomeMsgBoxResult;
import com.jyt.baseapp.util.Cache;
import com.jyt.baseapp.util.IntentHelper;
import com.jyt.baseapp.util.T;

import butterknife.BindView;
import butterknife.ButterKnife;
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
                        if (response.data!=null){
                            textSysDate.setText(response.data.createdTime);
                            textSysmsg.setText(response.data.content);

                            layoutSys.setVisibility(View.VISIBLE);
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
                        if (response.data!=null){
                            textOrderDate.setText(response.data.createdTime);
                            textOrdermsg.setText(response.data.content);

                            layoutOrder.setVisibility(View.VISIBLE);
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
