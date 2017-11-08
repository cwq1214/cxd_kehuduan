package com.cxd.khd.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cxd.khd.R;
import com.cxd.khd.api.BeanCallback;
import com.cxd.khd.api.Http;
import com.cxd.khd.entity.ALiPayResult;
import com.cxd.khd.entity.Address;
import com.cxd.khd.entity.BaseJson;
import com.cxd.khd.entity.CountMoneyResult;
import com.cxd.khd.entity.ExpressCompany;
import com.cxd.khd.entity.OrderTrackResult;
import com.cxd.khd.entity.SKOrderDetailResult;
import com.cxd.khd.entity.SKSendDefaultInfoResult;
import com.cxd.khd.entity.Track;
import com.cxd.khd.bean.WeiXinPayResult;
import com.cxd.khd.util.IntentHelper;
import com.cxd.khd.util.L;
import com.cxd.khd.util.T;
import com.cxd.khd.view.dialog.PayDialog;
import com.cxd.khd.view.dialog.TextDoneDialog;
import com.cxd.khd.wxapi.WXPayEntryActivity;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by chenweiqi on 2017/6/15.
 */

public class SKSendActivity extends BaseActivity {
    @BindView(R.id.text_orderNum)
    TextView textOrderNum;
    @BindView(R.id.text_state)
    TextView textState;
    @BindView(R.id.layout_orderState)
    RelativeLayout layoutOrderState;
    @BindView(R.id.text_newOrderState)
    TextView textNewOrderState;
    @BindView(R.id.text_newOrderDate)
    TextView textNewOrderDate;
    @BindView(R.id.img_orderDetail)
    ImageView imgOrderDetail;
    @BindView(R.id.layout_orderProgress)
    RelativeLayout layoutOrderProgress;
    @BindView(R.id.view_state_divider)
    View viewStateDivider;
    @BindView(R.id.text_send_name)
    TextView textSendName;
    @BindView(R.id.text_send_phone)
    TextView textSendPhone;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.img_send)
    ImageView imgSend;
    @BindView(R.id.layout_send)
    RelativeLayout layoutSend;
    @BindView(R.id.text_receive_name)
    TextView textReceiveName;
    @BindView(R.id.text_receive_phone)
    TextView textReceivePhone;
    @BindView(R.id.text_receiveAddress)
    TextView textReceiveAddress;
    @BindView(R.id.img_receive)
    ImageView imgReceive;
    @BindView(R.id.layout_receive)
    RelativeLayout layoutReceive;
    @BindView(R.id.label_type)
    TextView labelType;
    @BindView(R.id.text_type)
    TextView textType;
    @BindView(R.id.layout_type)
    LinearLayout layoutType;
    @BindView(R.id.label_weight)
    TextView labelWeight;
    @BindView(R.id.text_weight)
    TextView textWeight;
    @BindView(R.id.label_weight_symbol)
    TextView labelWeightSymbol;
    @BindView(R.id.layout_weight)
    LinearLayout layoutWeight;
    @BindView(R.id.label_volume)
    TextView labelVolume;
    @BindView(R.id.text_volume)
    TextView textVolume;
    @BindView(R.id.label_volume_symbol)
    TextView labelVolumeSymbol;
    @BindView(R.id.layout_volume)
    LinearLayout layoutVolume;
    @BindView(R.id.text_value)
    TextView textValue;
    @BindView(R.id.layout_value)
    LinearLayout layoutValue;
    @BindView(R.id.img_goodsDetail)
    ImageView imgGoodsDetail;
    @BindView(R.id.layout_goods_detail)
    RelativeLayout layoutGoodsDetail;
    @BindView(R.id.label_payType)
    TextView labelPayType;
    @BindView(R.id.text_payType)
    TextView textPayType;
    @BindView(R.id.layout_payType)
    LinearLayout layoutPayType;
    @BindView(R.id.label_company)
    TextView labelCompany;
    @BindView(R.id.text_company)
    TextView textCompany;
    @BindView(R.id.layout_company)
    LinearLayout layoutCompany;
    @BindView(R.id.label_keepValue)
    TextView labelKeepValue;
    @BindView(R.id.text_keepValue)
    TextView textKeepValue;
    @BindView(R.id.layout_keepValue)
    LinearLayout layoutKeepValue;
    @BindView(R.id.img_pay)
    ImageView imgPay;
    @BindView(R.id.layout_pay)
    RelativeLayout layoutPay;
    @BindView(R.id.label_keepValuePrice)
    TextView labelKeepValuePrice;
    @BindView(R.id.text_keepValuePrice)
    TextView textKeepValuePrice;
    @BindView(R.id.layout_keepValuePrice)
    LinearLayout layoutKeepValuePrice;
    @BindView(R.id.label_otherPrice)
    TextView labelOtherPrice;
    @BindView(R.id.text_otherPrice)
    TextView textOtherPrice;
    @BindView(R.id.layout_otherPrice)
    LinearLayout layoutOtherPrice;
    @BindView(R.id.label_expressPrice)
    TextView labelExpressPrice;
    @BindView(R.id.text_expressPrice)
    TextView textExpressPrice;
    @BindView(R.id.layout_expressPrice)
    LinearLayout layoutExpressPrice;
    @BindView(R.id.text_allPrice)
    TextView textAllPrice;
    @BindView(R.id.btn_scanIdCard)
    TextView btnScanIdCard;
    @BindView(R.id.btn_done)
    TextView btnDone;
    @BindView(R.id.layout_btnGroup)
    LinearLayout layoutBtnGroup;
    @BindView(R.id.btn_wait)
    TextView btnWait;
    @BindView(R.id.img_send_1)
    ImageView imgSend1;
    @BindView(R.id.img_send_2)
    ImageView imgSend2;
    @BindView(R.id.img_receive_1)
    ImageView imgReceive1;
    @BindView(R.id.img_receive_2)
    ImageView imgReceive2;
    @BindView(R.id.text_send_no_content)
    TextView textSendNoContent;
    @BindView(R.id.text_receive_no_content)
    TextView textReceiveNoContent;
    @BindView(R.id.text_servicePrice)
    TextView textServicePrice;
    @BindView(R.id.text_discountPrice)
    TextView textDiscountPrice;
    @BindView(R.id.text_realServicePrice)
    TextView textRealServicePrice;
    @BindView(R.id.layout_servicePrice)
    LinearLayout layoutServicePrice;
    @BindView(R.id.view_servicePriceDivider)
    View viewServicePriceDivider;
    @BindView(R.id.view_payDivider)
    View viewPayDivider;


    String orderId;
    SKSendDefaultInfoResult skSendDefaultInfoResult;
    ExpressCompany expressCompany;
    Address senderAddress;
    Address receiverAddress;
    OrderTrackResult orderTrackResult;
    SKOrderDetailResult skOrderDetailResult;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sk_send;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        labelVolumeSymbol.setText(Html.fromHtml("m<sup>3</sup>"));

        Intent intent = getIntent();
        if (intent != null) {
            orderId = intent.getStringExtra(IntentHelper.KEY_ORDER_ID);
            String titleText = intent.getStringExtra(IntentHelper.KEY_TITLE);
            if (!TextUtils.isEmpty(titleText)){
                textTitle.setText(titleText);
            }
        }

        if (TextUtils.isEmpty(orderId)) {//散客寄件
            layoutOrderProgress.setVisibility(View.GONE);
            layoutOrderState.setVisibility(View.GONE);
            viewStateDivider.setVisibility(View.GONE);
            layoutBtnGroup.setVisibility(View.GONE);
            haveSendAddress(null);
            haveReceiveAddress(null);

            getDefaultInfo();
            getSKAddress();
        } else {

            layoutServicePrice.setVisibility(View.GONE);
            viewServicePriceDivider.setVisibility(View.GONE);
            btnWait.setVisibility(View.GONE);
            layoutBtnGroup.setVisibility(View.GONE);
            viewPayDivider.setVisibility(View.GONE);
            textSendNoContent.setVisibility(View.GONE);
            textReceiveNoContent.setVisibility(View.GONE);

            imgSend.setVisibility(View.GONE);
            imgReceive.setVisibility(View.GONE);
            imgGoodsDetail.setVisibility(View.GONE);
            imgPay.setVisibility(View.GONE);
            getSKOrderDetail();
            getSKTrackOrder();
        }
    }

    public void haveSendAddress(Address address) {
        senderAddress = address;
        boolean have = address != null;
        textSendNoContent.setVisibility(have ? View.GONE : View.VISIBLE);
        imgSend1.setVisibility(have ? View.VISIBLE : View.INVISIBLE);
        imgSend2.setVisibility(have ? View.VISIBLE : View.INVISIBLE);
        textSendName.setVisibility(have ? View.VISIBLE : View.INVISIBLE);
        textSendPhone.setVisibility(have ? View.VISIBLE : View.INVISIBLE);
        textAddress.setVisibility(have ? View.VISIBLE : View.INVISIBLE);

        if (have) {
            textSendName.setText(address.name);
            textSendPhone.setText(address.mobile);
            textAddress.setText(address.address);
        }
    }

    public void haveReceiveAddress(Address address) {
        receiverAddress = address;
        boolean have = address != null;
        textReceiveNoContent.setVisibility(have ? View.GONE : View.VISIBLE);
        imgReceive1.setVisibility(have ? View.VISIBLE : View.INVISIBLE);
        imgReceive2.setVisibility(have ? View.VISIBLE : View.INVISIBLE);
        textReceiveName.setVisibility(have ? View.VISIBLE : View.INVISIBLE);
        textReceivePhone.setVisibility(have ? View.VISIBLE : View.INVISIBLE);
        textReceiveAddress.setVisibility(have ? View.VISIBLE : View.INVISIBLE);

        if (have) {
            textReceiveName.setText(address.name);
            textReceivePhone.setText(address.mobile);
            textReceiveAddress.setText(address.address);
        }
    }
    @OnClick(R.id.layout_orderProgress)
    public void onLayoutOrderProgressClick() {
        if (orderTrackResult==null){
            return;
        }
        IntentHelper.openTrackOrderActivity(getContext(),orderTrackResult);
    }

    @OnClick(R.id.layout_send)
    public void onEditSendAddressClick() {
        if (TextUtils.isEmpty(orderId)) {
            IntentHelper.openSenderAddressListActivityForResult(getContext());
        }
    }

    @OnClick(R.id.layout_receive)
    public void onEditReceiveAddressClick() {
        if (TextUtils.isEmpty(orderId)) {
            IntentHelper.openReceiverAddressListActivityForResult(getContext());
        }
    }

    @OnClick(R.id.layout_goods_detail)
    public void onGoodsDetailClick() {
        if (TextUtils.isEmpty(orderId)) {
            IntentHelper.openGoodsDetailActivity(getContext(), textType.getText().toString(), skSendDefaultInfoResult.goodsType, textWeight.getText().toString(), skSendDefaultInfoResult.weight, textVolume.getText().toString(), skSendDefaultInfoResult.volume, textValue.getText().toString());
        }
    }

    @OnClick(R.id.layout_pay)
    public void onPayDetailClick() {
        if (TextUtils.isEmpty(orderId)) {
            IntentHelper.openOrderDetailActivityForResult(getContext()
                    , expressCompany
                    , skSendDefaultInfoResult.express
                    , textPayType.getText().toString()
                    , textKeepValue.getText().toString()
                    , null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == IntentHelper.REQUIRE_CODE_SEL_SENDER_ADDRESS) {
            Address address = data.getParcelableExtra(IntentHelper.KEY_ADDRESS);
            haveSendAddress(address);
        } else if (resultCode == RESULT_OK
                && requestCode == IntentHelper.REQUIRE_CODE_SEL_RECEIVEER_ADDRESS) {
            Address address = data.getParcelableExtra(IntentHelper.KEY_ADDRESS);
            haveReceiveAddress(address);
        } else if (requestCode == IntentHelper.REQUIRE_CODE_EDIT_ORDER_PAY_INFO && resultCode == RESULT_OK) {
            textPayType.setText(data.getStringExtra(IntentHelper.KEY_PAY_TYPE));
            textKeepValue.setText(data.getStringExtra(IntentHelper.KEY_KEEP_VALUE));
//            textOtherPrice.setText(data.getStringExtra(IntentHelper.KEY_COST));
            expressCompany = data.getParcelableExtra(IntentHelper.KEY_COMPANY);
            textCompany.setText(expressCompany.eName);
            textDiscountPrice.setText(expressCompany.subsidy);
            countFinalMoney();
            countMoney();
        } else if (requestCode == IntentHelper.REQUIRE_CODE_EDIT_GOODS_INFO && resultCode == RESULT_OK) {
            textType.setText(data.getStringExtra(IntentHelper.KEY_TYPE));
            textValue.setText(data.getStringExtra(IntentHelper.KEY_VALUE));
            textWeight.setText(data.getStringExtra(IntentHelper.KEY_WEIGHT));
            textVolume.setText(data.getStringExtra(IntentHelper.KEY_VOLUME));
            countMoney();
        } else if (requestCode == IntentHelper.REQUIRE_PAY && resultCode == RESULT_OK){
            boolean payResult = data.getBooleanExtra(IntentHelper.KEY_PAY_RESULT,false);
            if (payResult){

                finish();
            }
        }
    }


    private void getDefaultInfo() {
        Http.getSKSendDefaultInfo(getContext(), new BeanCallback<BaseJson<SKSendDefaultInfoResult>>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson<SKSendDefaultInfoResult> response, Exception e) {
                super.onResult(success, response, e);
                if (success) {
                    T.showShort(getContext(), response.forUser);
                    if (response.ret) {
                        skSendDefaultInfoResult = response.data;

                    }
                } else {
                    T.showShort(getContext(), e.getMessage());
                }
            }
        });
    }

    private void countMoney() {


        //支付方式
        String pt = "";
        String paytype = textPayType.getText().toString();
        if (paytype.contains("线上支付")) {
            pt = "1";
        } else if (paytype.contains("线下支付")) {
            pt = "2";
        } else if (paytype.contains("到付")) {
            pt = "3";
        }
        //是否保价
        String keepValue = textKeepValue.getText().toString().equals("保价") ? "1" : "0";

        //保价金额
        String keepValuePrice = textValue.getText().toString();
        //体积
        String volume = textVolume.getText().toString();
        //类型
        String type = textType.getText().toString();
        //重量
        String weight = textWeight.getText().toString();

        if (TextUtils.isEmpty(pt)
                || TextUtils.isEmpty(keepValue)
                || TextUtils.isEmpty(keepValuePrice)
                || TextUtils.isEmpty(volume)
                || TextUtils.isEmpty(type)
                || TextUtils.isEmpty(weight)
                ||expressCompany==null
                ) {

            return;
        }

        Http.SkCountMoney(getContext()
                , keepValue
                , keepValuePrice
                , pt
                , volume
                , weight
                , type
                ,expressCompany.expressId
                , new BeanCallback<BaseJson<CountMoneyResult>>(getContext()) {
                    @Override
                    public void onResult(boolean success, BaseJson<CountMoneyResult> response, Exception e) {
                        super.onResult(success, response, e);
                        if (success) {
                            T.showShort(getContext(), response.forUser);
                            if (response.ret) {
                                textAllPrice.setText(response.data.totalPrice);
                                textExpressPrice.setText(response.data.price);
                                textKeepValuePrice.setText(response.data.insuredPrice);
                                textServicePrice.setText(response.data.servicePrice);
//                                textOtherPrice.setText(response.data.otherPrice);


                                countFinalMoney();
                            }
                        } else {
                            T.showShort(getContext(), e.getMessage());
                        }
                    }
                }
        );


    }


    private void countFinalMoney() {
        String servicePrice = textServicePrice.getText().toString();
        String discount = textDiscountPrice.getText().toString();

        double finalPrice = Double.valueOf(servicePrice) - Double.valueOf(discount);

        DecimalFormat format = new DecimalFormat("0.00");
        finalPrice = finalPrice > 0 ? finalPrice : 0;
        textRealServicePrice.setText(format.format(finalPrice) + "");
    }

    //下单支付
    @OnClick(R.id.btn_wait)
    public void onBtnPayClick() {
        if (senderAddress == null) {
            T.showShort(getContext(), "请选择寄件人");
            return;
        }
        if (receiverAddress == null) {
            T.showShort(getContext(), "请选择收件人");
            return;
        }
        if (expressCompany==null){
            T.showShort(getContext(),"请选择收件公司");
            return;
        }


        TextDoneDialog doneDialog = new TextDoneDialog(getContext());
        doneDialog.setDoneListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                submitOrderALi();
                String realPrice = textRealServicePrice.getText().toString();
                if (!TextUtils.isEmpty(realPrice)
                        &&Double.valueOf(realPrice)==0){

                    submitOrder(false,0);

                }else {
                    PayDialog payDialog = new PayDialog(getContext());
                    payDialog.setOnDoneClickListener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 1) {
                                submitOrder(true,1);
                            } else if (which == 2) {
                                submitOrder(true,2);
                            }
                            dialog.dismiss();
                        }
                    });
                    payDialog.show();
                }
                dialog.dismiss();
            }
        });
        doneDialog.setTextMsg("下单后，订单信息您将无法修改，是否确认信息？");
        doneDialog.show();
    }

    private void submitOrder(boolean needPay,int type) {
        String sendName = senderAddress.name;
        String sendMobile = senderAddress.mobile;
        String sendAddress = senderAddress.address;
        String receiveAddress = receiverAddress.address;
        String receiveMobile = receiverAddress.mobile;
        String receiveName = receiverAddress.name;
        String value = textValue.getText().toString();

        String pt = "";
        String paytype = textPayType.getText().toString();
        if (paytype.contains("线上支付")) {
            pt = "1";
        } else if (paytype.contains("线下支付")) {
            pt = "2";
        } else if (paytype.contains("到付")) {
            pt = "3";
        }

        String payType = pt;
        String servicePrice = textRealServicePrice.getText().toString();
        String price = textExpressPrice.getText().toString();
        String insured = textKeepValue.getText().toString().equals("保价") ? "1" : "0";
        String insuredPrice = textKeepValuePrice.getText().toString();
        String subsidy = textDiscountPrice.getText().toString();
        String totalPrice = textAllPrice.getText().toString();
        String volume = textVolume.getText().toString();
        String weight = textWeight.getText().toString();
        String goodsType = textType.getText().toString();
        String expressCompany = textCompany.getText().toString();
        String longitude = senderAddress.longitude;
        String latitude = senderAddress.latitude;


        if (!needPay){
            servicePrice = "0";
            Http.submitOrderWithoutMoney(getContext(), sendName, sendMobile, sendAddress, receiveAddress, receiveMobile, receiveName, value, payType, servicePrice, price, insured, insuredPrice, subsidy, totalPrice, volume, weight, goodsType, expressCompany, longitude,latitude,SKSendActivity.this.expressCompany.expressId
                    , new BeanCallback<BaseJson>(getContext()) {
                        @Override
                        public void onResult(boolean success, BaseJson response, Exception e) {
                            super.onResult(success, response, e);
                            if (success) {
                                T.showShort(getContext(), response.forUser);
                                if (response.ret) {
                                    finish();
                                    IntentHelper.openSKOrderListActivity(getContext());
                                }
                            } else {
                                T.showShort(getContext(), e.getMessage());
                            }
                        }
                    });
        }else {
            if (type==2) {//支付宝支付
                Http.SKSubmitOrder(getContext(), sendName, sendMobile, sendAddress, receiveAddress, receiveMobile, receiveName, value, payType, servicePrice, price, insured, insuredPrice, subsidy, totalPrice, volume, weight, goodsType, expressCompany, longitude, latitude
                        , new BeanCallback<BaseJson<ALiPayResult>>(getContext()) {
                            @Override
                            public void onResult(boolean success, BaseJson<ALiPayResult> response, Exception e) {
                                super.onResult(success, response, e);
                                if (success) {
                                    T.showShort(getContext(), response.forUser);
                                    if (response.ret) {
                                        IntentHelper.openPayActivityForResult_ALI(getContext(), response.data.sign, "下单", textRealServicePrice.getText().toString(), true);
//                                finish();
                                    }
                                } else {
                                    T.showShort(getContext(), e.getMessage());
                                }
                            }
                        });
            }else if (type == 1){//微信支付
                Http.SKSubmitOrder_WEIXIN(getContext(), sendName, sendMobile, sendAddress, receiveAddress, receiveMobile, receiveName, value, payType, servicePrice, price, insured, insuredPrice, subsidy, totalPrice, volume, weight, goodsType, expressCompany, longitude, latitude
                        , new BeanCallback<BaseJson<WeiXinPayResult>>(getContext()) {
                            @Override
                            public void onResult(boolean success, BaseJson<WeiXinPayResult> response, Exception e) {
                                super.onResult(success, response, e);
                                if (success) {
                                    T.showShort(getContext(), response.forUser);
                                    if (response.ret) {
                                        IntentHelper.openPayActivityForResult_WEIXIN(getContext(), response.data.partnerId
                                                ,response.data.prepayId
                                                ,response.data.timeStamp+""
                                                ,response.data.paySign
                                                ,response.data.packageValue
                                                ,response.data.nonceStr, "下单", textRealServicePrice.getText().toString(), true);
//                                finish();
                                        L.e(WXPayEntryActivity.class.getPackage().toString());
                                        L.e(this.getClass().getPackage().toString());

                                    }
                                } else {
                                    T.showShort(getContext(), e.getMessage());
                                }
                            }
                        });
            }
        }
    }


    private void getSKAddress(){
        Http.getSKAddressList(getContext(), new BeanCallback<BaseJson<List<Address>>>(getContext().getApplicationContext()) {
            @Override
            public void onResult(boolean success, BaseJson<List<Address>> response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(),response.forUser);
                    if (response.ret){
                        if (response.data!=null)
                        for (Address a:
                             response.data) {
                            if (a!=null&&"1".equals(a.isdefault)){
                                haveSendAddress(a);
                            }
                        }
                    }
                }else {
                    T.showShort(getContext(),e.getMessage());
                }

            }
        });
    }

    //获取详情
    private void getSKOrderDetail() {
        Http.getSKOrderDetail(getContext(), orderId, new BeanCallback<BaseJson<SKOrderDetailResult>>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson<SKOrderDetailResult> response, Exception e) {
                super.onResult(success, response, e);
                if (success) {
                    T.showShort(getContext(), response.forUser);
                    if (response.ret) {
                        skOrderDetailResult = response.data;
                        initView(response.data);
                    }
                } else {
                    T.showShort(getContext(), e.getMessage());
                }
            }
        });
    }

    private void initView(SKOrderDetailResult result) {
        textOrderNum.setText("订单号" + result.orderNo);
        textState.setText(result.receiveStateMsg);
        textSendName.setText(result.sendName);
        textSendPhone.setText(result.sendMobile);
        textAddress.setText(result.sendAddress);
        textReceiveName.setText(result.receiveName);
        textReceivePhone.setText(result.receiveMobile);
        textReceiveAddress.setText(result.receiveAddress);
        textType.setText(result.goodsType);
        textWeight.setText(result.weight);
        textVolume.setText(result.volume);
        textValue.setText(result.value);
        if (!TextUtils.isEmpty(result.payType)){
            String text = null;
            if ("1".equals(result.payType)){
                text = "线上支付";
            }else if ("2".equals(result.payType)){
                text = "线下支付";
            }else if ("3".equals(result.payType)){
                text = "到付";
            }
            textPayType.setText(text);
        }

        textCompany.setText(result.expressCompany);
        if (result.insured!=null) {
            textKeepValue.setText(result.insured.equals("0") ? "不保价" : "保价");
        }else {
            textKeepValue.setText("不保价");
        }
        textKeepValuePrice.setText(result.insuredPrice);
        textOtherPrice.setText("0");
        textExpressPrice.setText(result.price);
        textAllPrice.setText(result.totalPrice);

        if ("待支付".equals(result.receiveStateMsg)){
            layoutBtnGroup.setVisibility(View.VISIBLE);
            btnDone.setText("支付运费");
        }

    }

    @OnClick(R.id.btn_scanIdCard)
    public void onCancelClick(){
        if (skOrderDetailResult!=null){
            TextDoneDialog doneDialog = new TextDoneDialog(getContext());
            doneDialog.setTextMsg("确定取消订单吗？");
            doneDialog.setDoneListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Http.cancelOrder(getContext(), orderId, new BeanCallback<BaseJson>(getContext()) {
                        @Override
                        public void onResult(boolean success, BaseJson response, Exception e) {
                            super.onResult(success, response, e);
                            if (success){
                                T.showShort(getContext(),response.forUser);
                                if (response.ret){
                                    getSKOrderDetail();
                                    getSKTrackOrder();
                                }
                            }else {
                                T.showShort(getContext(),e.getMessage());
                            }
                        }
                    });
                    dialog.dismiss();
                }
            });
            doneDialog.show();
        }
    }

    @OnClick(R.id.btn_done)
    public void onDoneClick(){
        if (skOrderDetailResult!=null){
            if ("待支付".equals(skOrderDetailResult.receiveStateMsg)){
                PayDialog payDialog = new PayDialog(getContext());
                payDialog.setOnDoneClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 1) {
                            Http.payExpressPrice_WEIXIN(getContext(),orderId, skOrderDetailResult.totalPrice, new BeanCallback<BaseJson<WeiXinPayResult>>(getContext()) {
                                @Override
                                public void onResult(boolean success, BaseJson<WeiXinPayResult> response, Exception e) {
                                    super.onResult(success, response, e);
                                    if (success){
                                        T.showShort(getContext(),response.forUser);
                                        if (response.ret)
                                            IntentHelper.openPayActivityForResult_WEIXIN(getContext(), response.data.partnerId
                                                    ,response.data.prepayId
                                                    ,response.data.timeStamp+""
                                                    ,response.data.paySign
                                                    ,response.data.packageValue
                                                    ,response.data.nonceStr,"支付运费",skOrderDetailResult.totalPrice,true);

                                    }else {
                                        T.showShort(getContext(),e.getMessage());
                                    }
                                }
                            });
                        } else if (which == 2) {
                            Http.payExpressPrice(getContext(),orderId, skOrderDetailResult.totalPrice, new BeanCallback<BaseJson<ALiPayResult>>(getContext()) {
                                @Override
                                public void onResult(boolean success, BaseJson<ALiPayResult> response, Exception e) {
                                    super.onResult(success, response, e);
                                    if (success){
                                        T.showShort(getContext(),response.forUser);
                                        if (response.ret)
                                            IntentHelper.openPayActivityForResult_ALI(getContext(),response.data.sign,"支付运费",skOrderDetailResult.totalPrice,true);

                                    }else {
                                        T.showShort(getContext(),e.getMessage());
                                    }
                                }
                            });
                        }

                        dialog.dismiss();
                    }

                });
                payDialog.show();
            }
        }
    }

    private void getSKTrackOrder(){
        Http.getSKTrackOrder(getContext(), orderId, new BeanCallback<BaseJson<OrderTrackResult>>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson<OrderTrackResult> response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(),response.forUser);
                    if (response.ret){
                        orderTrackResult = response.data;
                        setViewContentOrderTrack(response.data);
                    }
                }else {
                    T.showShort(getContext(),e.getMessage());
                }
            }
        });
    }
    private void setViewContentOrderTrack(OrderTrackResult result){
        if (result.track!=null&&result.track.size()!=0) {
            Track track = result.track.get(result.track.size()-1);
            textNewOrderState.setText(track.msg);
            textNewOrderDate.setText(track.msgTime);
        }else {
            textNewOrderState.setText("暂无订单信息");
        }
    }

    @Override
    public void onBtnBackClick() {
        new AlertDialog.Builder(getContext()).setMessage("确定要退出吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();

    }


    @Override
    public void onBackPressed() {
        onBtnBackClick();
//        super.onBackPressed();
    }
}
