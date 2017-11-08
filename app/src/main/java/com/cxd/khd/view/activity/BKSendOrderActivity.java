package com.cxd.khd.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.cxd.khd.R;
import com.cxd.khd.adapter.BKCreateOrderListAdapter;
import com.cxd.khd.api.BeanCallback;
import com.cxd.khd.api.Http;
import com.cxd.khd.entity.BKOrderListItem;
import com.cxd.khd.entity.BaseJson;
import com.cxd.khd.entity.DefaultValueResult;
import com.cxd.khd.util.CaptureManager;
import com.cxd.khd.util.IntentHelper;
import com.cxd.khd.util.T;
import com.cxd.khd.view.dialog.InputDialog;
import com.cxd.khd.view.dialog.TextDoneDialog;
import com.cxd.khd.view.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/6/20.
 */

public class BKSendOrderActivity extends BaseActivity {


    @BindView(R.id.scannerView)
    DecoratedBarcodeView scannerView;
    @BindView(R.id.text_packNo)
    EditText textPackNo;
    @BindView(R.id.text_areaCode)
    EditText textAreaCode;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_add)
    TextView btnAdd;
    @BindView(R.id.input_autoText)
    EditText inputAutoText;
    @BindView(R.id.switchOnOff)
    TextView switchOnOff;

    private CaptureManager capture;
    BKCreateOrderListAdapter adapter;

    String defaultValue = "0.00";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bk_send_order;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnFunction.setText("下单");
        capture = new CaptureManager(this, scannerView);
        capture.setOnScanResultCallback(new CaptureManager.OnScanResultCallback() {
            @Override
            public void result(Intent intent) {
                //扫码结果回调
                IntentResult result = IntentIntegrator.parseActivityResult(IntentIntegrator.REQUEST_CODE, RESULT_OK, intent);
                //扫描结果
                String content = result.getContents();

//                content = "1111";

                decodeResult(content);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                scannerView.resume();

            }
        });
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decodeContinuous();

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_rcv));
        recyclerview.addItemDecoration(itemDecoration);
        recyclerview.setAdapter(adapter = new BKCreateOrderListAdapter());
        adapter.setOnDelImageClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder, Object data, int position) {
                adapter.getDataList().remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        inputAutoText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains("\n")){
                    decodeResult(inputAutoText.getText().toString().replace("\n",""));
                    inputAutoText.setText("");
                }
            }
        });

        getDefaultValue();
    }

    boolean open = true;
    @OnClick(R.id.switchOnOff)
    public void onClickSwitchOnOff(){
        if (open){
            scannerView.pauseAndWait();
        }else {
            scannerView.resume();

        }
        open = !open;

    }


    private void getDefaultValue(){
        Http.bkGetDefaultValue(getContext(), new BeanCallback<BaseJson<DefaultValueResult>>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson<DefaultValueResult> response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(),response.forUser);
                    if (response.ret){
                        if (response.data!=null
                                &&!TextUtils.isEmpty(response.data.default_value)){
                            defaultValue = response.data.default_value;
                        }
                    }

                }else {
                    T.showShort(getContext(),e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }


    //解析扫码内容
    private void decodeResult(String result) {

        if (isPackNo(result)) {
            setTextPackNo(result);
        } else if (isAreaNo(result)) {
            setTextAreaCode(result);
        } else {
            AddOrder(result);
        }
    }
    //下单
    @Override
    public void onFunctionClick() {
        super.onFunctionClick();
        TextDoneDialog doneDialog = new TextDoneDialog(getContext());
        doneDialog.setTextMsg("是否确定要下单");
        doneDialog.setDoneListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                submitOrder();
                dialog.dismiss();
            }
        });
        doneDialog.show();
    }

    private void submitOrder(){
        String packNo = textPackNo.getText().toString();
        String areaNo = textAreaCode.getText().toString();
        if (!isPackNo(packNo)){
            T.showShort(getContext(),"包号码输入有误，请检查");
            return;
        }
        if (!isAreaNo(areaNo)){
            T.showShort(getContext(),"区域编码输入有误，请检查");
            return;
        }
        if (adapter.getDataList()==null||adapter.getDataList().size()==0){
            T.showShort(getContext(),"快递运单号为空，请先填写");
            return;
        }
        List<String> orderNos = new ArrayList<>();
        List<String> values = new ArrayList<>();
        List<BKOrderListItem> listItems = adapter.getDataList();
        for (int i=0,max = listItems.size();i<max;i++){
            orderNos.add(listItems.get(i).orderNo);
            values.add(listItems.get(i).value);
        }


        Http.BKSubmitSendOrder(getContext(), areaNo, packNo, orderNos, values, new BeanCallback<BaseJson<SubmitOrderResult>>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson<SubmitOrderResult> response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(),response.forUser);
                    if (response.ret){
                        IntentHelper.openPackDetailActivity(getContext(), response.data.packageId);
                        finish();
                    }
                }else {
                    T.showShort(getContext(),e.getMessage());
                }
            }
        });
    }

    private boolean isPackNo(String packNo){
        //包号码   666开头的8位数字
        Pattern patternPackNo = Pattern.compile("^666\\d{5}$");
        return patternPackNo.matcher(packNo).find();
    }

    private boolean isAreaNo(String areaNo){
        //区域编码 三个数字 + 1个字母 + 三个数字
        Pattern patternArearNo = Pattern.compile("^(\\d{3})[A-Za-z](\\d{3})$");
        return patternArearNo.matcher(areaNo).find();
    }


    private void setTextPackNo(final String packNo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textPackNo.setText(packNo);
            }
        });
    }

    private void setTextAreaCode(final String areaCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textAreaCode.setText(areaCode);
            }
        });
    }

    private void AddOrder(String orderNo) {
        List list = adapter.getDataList();
        if (list == null){
            list = new ArrayList();
        }
        list.add(0,new BKOrderListItem(orderNo,defaultValue));
        adapter.setDataList(list);
        adapter.notifyDataSetChanged();


    }

    @OnClick(R.id.btn_add)
    public void onBtnAddClick(){
        InputDialog dialog = new InputDialog(getContext());
        dialog.setTitle("请输入运单号");
        dialog.setOnDoneClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(((InputDialog) dialog).getInputContent())){
                    T.showShort(getContext(),"请输入运单号");
                    return;
                }
                AddOrder(((InputDialog) dialog).getInputContent());
                dialog.dismiss();
            }
        });
        dialog.setOnCancelClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public class SubmitOrderResult{
        public String packageId;
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
