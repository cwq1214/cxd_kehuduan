package com.jyt.baseapp.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.entity.OrderMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/6/2.
 */

public class RobOrderDialog extends AlertDialog {
    @BindView(R.id.msg)
    TextView msg;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text_deadline)
    TextView textDeadline;
    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.btn_done)
    TextView btnDone;

    OrderMessage orderMessage;

    OnClickListener onCancelClickListener;
    OnClickListener onDoneClickListener;



    public RobOrderDialog(@NonNull Context context) {
        super(context, R.style.customDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rob_order);
        ButterKnife.bind(this);

        if (orderMessage!=null) {
            msg.setText(Html.fromHtml(orderMessage.content));
            textDeadline.setText(orderMessage.createdTime);
        }

    }


    public void setMessage(OrderMessage orderMessage){
        this.orderMessage = orderMessage;
    }


    public void setOnCancelClickListener(OnClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
    }

    public void setOnDoneClickListener(OnClickListener onDoneClickListener) {
        this.onDoneClickListener = onDoneClickListener;
    }

    @OnClick(R.id.btn_cancel)
    public void onCancelClick(){
        if (onCancelClickListener!=null){
            onCancelClickListener.onClick(this,0);
        }else {
            dismiss();
        }
    }

    @OnClick(R.id.btn_done)
    public void onDoneClick(){
        if (onDoneClickListener!=null){
            onDoneClickListener.onClick(this,0);
        }else {
            dismiss();
        }
    }
}
