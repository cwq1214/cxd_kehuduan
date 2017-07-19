package com.jyt.baseapp.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Http;
import com.jyt.baseapp.entity.BaseJson;
import com.jyt.baseapp.entity.PackageDetailResult;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.util.IntentHelper;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.dialog.InputDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URLDecoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/6/21.
 */

public class PackDetailActivity extends BaseActivity {
    @BindView(R.id.text_packNo)
    TextView textPackNo;
    @BindView(R.id.text_areaNo)
    TextView textAreaNo;
    @BindView(R.id.layout_orderList)
    RelativeLayout layoutOrderList;
    @BindView(R.id.text_marks)
    TextView textMarks;
    @BindView(R.id.layout_progress)
    LinearLayout layoutProgress;
    @BindView(R.id.btn_done)
    TextView btnDone;


    String packageId;

    PackageDetailResult result;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pack_detail;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent!=null){
            packageId = intent.getStringExtra(IntentHelper.KEY_PACKAGE_ID);
        }

        getPackDetail(packageId);
    }

    private void getPackDetail(String packageId){
        Http.BKPackageDetail(getContext(), packageId, new BeanCallback<BaseJson<PackageDetailResult>>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson<PackageDetailResult> response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(),response.forUser);
                    if (response.ret){
                        result = response.data;
                        if (!TextUtils.isEmpty(result.remark))
                            result.remark = URLDecoder.decode(result.remark);
                        initView(response.data);
                    }
                }else {
                    T.showShort(getContext(),e.getMessage());
                }
            }
        });
    }


    private void initView(PackageDetailResult result){
        textPackNo.setText(result.packageNo);
        textAreaNo.setText(result.areaId);
        if (!TextUtils.isEmpty(result.remark)){
            JSONArray jsonArray = new JSONArray();
            StringBuffer stringBuffer = new StringBuffer();
            try {
                 jsonArray = new JSONArray(result.remark);

                for (int i=0;i<jsonArray.length();i++){
                    stringBuffer.append(i+1+"、"+jsonArray.get(i)+"\n");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            textMarks.setText(stringBuffer.toString());

        }

        layoutProgress.removeAllViews();

        for (int i=0;i<result.track.size();i++){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_order_progress,layoutProgress,false);
            TextView title = (TextView) view.findViewById(R.id.text_title);
            TextView date = (TextView) view.findViewById(R.id.text_date);

            title.setText(result.track.get(i).msg);
            date.setText(result.track.get(i).msgTime);

            if (i+1==result.track.size()){
                View line = view.findViewById(R.id.view_line);
                line.setVisibility(View.GONE);
            }

            layoutProgress.addView(view);
        }
    }

    @OnClick(R.id.layout_orderList)
    public void onOrderListClick(){
        IntentHelper.openBKPackOrderListActivity(getContext(),result.packageNo);
    }


    @OnClick(R.id.btn_done)
    public void onAddMarkClick(){
        InputDialog dialog = new InputDialog(getContext());
        dialog.setTitle("备注");
        dialog.setInputSingleLine(false);
        dialog.setInputMinHeight(DensityUtil.dpToPx(getContext(),100));
        dialog.setInputBackgroundDrawableId(R.drawable.bg_gray_border_angel);
        dialog.setOnDoneClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                JSONArray jsonArray = new JSONArray();
                if (!TextUtils.isEmpty(result.remark)) {
                    try {
                        jsonArray = new JSONArray(result.remark);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                jsonArray.put(((InputDialog) dialog).getInputContent().toString());
                addMark(jsonArray.toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void addMark(String mark){
        Http.updatePackMark(getContext(), mark, packageId, new BeanCallback<BaseJson>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(),response.forUser);
                    if (response.ret){
                        getPackDetail(packageId);
                    }
                }else {
                    T.showShort(getContext(),e.getMessage());
                }
            }
        });
    }
}
