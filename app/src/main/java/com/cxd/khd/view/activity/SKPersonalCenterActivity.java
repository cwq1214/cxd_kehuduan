package com.cxd.khd.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.cxd.khd.R;
import com.cxd.khd.api.BeanCallback;
import com.cxd.khd.api.Http;
import com.cxd.khd.entity.BKUserInfoResult;
import com.cxd.khd.entity.BaseJson;
import com.cxd.khd.entity.SKUserInfoResult;
import com.cxd.khd.util.Cache;
import com.cxd.khd.util.FileUtil;
import com.cxd.khd.util.IntentHelper;
import com.cxd.khd.util.T;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by chenweiqi on 2017/6/15.
 */

public class SKPersonalCenterActivity extends BaseActivity {
    @BindView(R.id.btn_logout)
    RelativeLayout btnLogout;
    @BindView(R.id.img_skAvatar)
    ImageView imgSkAvatar;
    @BindView(R.id.text_skName)
    TextView textSkName;
    @BindView(R.id.layout_address)
    RelativeLayout layoutAddress;
    @BindView(R.id.text_score)
    TextView textScore;
    @BindView(R.id.layout_sk)
    LinearLayout layoutSk;
    @BindView(R.id.img_bkAvatar)
    ImageView imgBkAvatar;
    @BindView(R.id.text_bkName)
    TextView textBkName;
    @BindView(R.id.layout_bk)
    LinearLayout layoutBk;


    boolean signed = false;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_sk_personal_center;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutSk.setVisibility(View.GONE);
        layoutBk.setVisibility(View.GONE);

        if (Cache.getInstance().getLoginType()==1){//sk
            layoutSk.setVisibility(View.VISIBLE);

            getSKUserInfo();
        }else if (Cache.getInstance().getLoginType() == 2){//bk
            layoutBk.setVisibility(View.VISIBLE);

            getBKUserInfo();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getSKUserInfo(){
        Http.getSKUserInfo(getContext(), new BeanCallback<BaseJson<SKUserInfoResult>>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson<SKUserInfoResult> response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(),response.forUser);
                    if (response.ret){
                        if (!TextUtils.isEmpty(response.data.userImg)) {
                            Glide.with(getContext()).load(response.data.userImg).bitmapTransform(new CenterCrop(getContext()),new CropCircleTransformation(getContext())).into(imgSkAvatar);
                        }
                        textSkName.setText(response.data.username);
                        textScore.setText(response.data.score);
                        signed = response.data.sign;
                        setSigned(signed);
                    }
                }else {
                    T.showShort(getContext(),e.getMessage());
                }
            }
        });
    }

    private void getBKUserInfo(){
        Http.getBKUserInfo(getContext(), new BeanCallback<BaseJson<BKUserInfoResult>>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson<BKUserInfoResult> response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(),response.forUser);
                    if (response.ret){
                        if (!TextUtils.isEmpty(response.data.companyImg)) {
                            Glide.with(getContext()).load(response.data.companyImg).bitmapTransform(new CenterCrop(getContext()),new CropCircleTransformation(getContext())).into(imgBkAvatar);
                        }
                        textBkName.setText(response.data.companyName);

                    }
                }else {
                    T.showShort(getContext(),e.getMessage());
                }
            }
        });
    }

    @OnClick(R.id.layout_bkSelImg)
    public void onSelBkAvatarClick(){
        IntentHelper.openPickPhotoActivityForResult(getContext(),456);

    }

    @OnClick(R.id.layout_modifySKAvatar)
    public void onChangeSKAvatarClick(){
        IntentHelper.openPickPhotoActivityForResult(getContext(),123);
    }

    @OnClick(R.id.layout_address)
    public void onSKAddress(){
        IntentHelper.openSKAddressListActivity(getContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK){
            Uri uri = IntentHelper.afterPickPhoto(data);
            Glide.with(getContext()).load(uri).bitmapTransform(new CenterCrop(getContext()),new CropCircleTransformation(getContext())).into(imgSkAvatar);

            modifySKAvatar(FileUtil.getPath(getContext(),uri));
        }else if (requestCode == 456 && resultCode == RESULT_OK){
            Uri uri = IntentHelper.afterPickPhoto(data);
            Glide.with(getContext()).load(uri).bitmapTransform(new CenterCrop(getContext()),new CropCircleTransformation(getContext())).into(imgBkAvatar);

            modifyBKAvatar(FileUtil.getPath(getContext(),uri));
        }
    }

    @Override
    public void onFunctionClick() {
        super.onFunctionClick();
        if (!signed){
            Http.SKSign(getContext(), new BeanCallback<BaseJson>(getContext()) {
                @Override
                public void onResult(boolean success, BaseJson response, Exception e) {
                    super.onResult(success, response, e);
                    if (success){
                        T.showShort(getContext(),response.forUser);
                        if (response.ret){
                            signed = true;
                            setSigned(true);
                            getSKUserInfo();
                        }
                    }else {
                        T.showShort(getContext(),e.getMessage());
                    }
                }
            });
        }else {
            T.showShort(getContext(),"今天已经签到了");
        }
    }

    private void setSigned(boolean signed){
        if (Cache.getInstance().getLoginType() == 1){//bk
            if (signed) {
                btnFunction.setBackground(getResources().getDrawable(R.mipmap.mine_icon_qiandao_pressed));
            }else {
                btnFunction.setBackground(getResources().getDrawable(R.mipmap.mine_icon_qiandao_unpressed));


            }
        }else {
            btnFunction.setVisibility(View.GONE);
        }
    }


    private void modifySKAvatar(String imagePath){
        Http.modifySKAvatar(getContext(), imagePath, new BeanCallback<BaseJson>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(),response.forUser);
                    if (response.ret){

                    }
                }else {
                    T.showShort(getContext(),e.getMessage());
                }
            }
        });
    }

    private void modifyBKAvatar(String imagePath){
        Http.modifyBKAvatar(getContext(), imagePath, new BeanCallback<BaseJson>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(),response.forUser);
                    if (response.ret){

                    }
                }else {
                    T.showShort(getContext(),e.getMessage());
                }
            }
        });
    }

    @OnClick(R.id.btn_logout)
    public void onLogoutClick(){
        new AlertDialog.Builder(getContext()).setMessage("确定要退出登录吗？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cache.getInstance().setUserId(null);
                Cache.getInstance().setToken(null);
                Cache.getInstance().setPhone(null);
                Cache.getInstance().setLoginType(-1);
                dialog.dismiss();
                finish();
                IntentHelper.exit(getContext());
                IntentHelper.openLoginActivity(getContext());
            }
        }).show();
    }
}

