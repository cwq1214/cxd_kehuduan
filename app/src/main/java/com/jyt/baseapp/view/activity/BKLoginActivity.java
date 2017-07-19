package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jyt.baseapp.App;
import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Http;
import com.jyt.baseapp.entity.BaseJson;
import com.jyt.baseapp.entity.LoginResult;
import com.jyt.baseapp.util.Cache;
import com.jyt.baseapp.util.IntentHelper;
import com.jyt.baseapp.util.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/6/20.
 */

public class BKLoginActivity extends BaseActivity {
    @BindView(R.id.input_username)
    EditText inputUsername;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.btn_login)
    TextView btnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bk_login;
    }

    @Override
    protected View getContentView() {
        return null;
    }


    @OnClick(R.id.btn_login)
    public void onLoginClick(){
        String userName = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        if (TextUtils.isEmpty(userName)){
            T.showShort(getContext(),"请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(password)){
            T.showShort(getContext(),"请输入密码");
            return;
        }
        Http.BKLogin(getContext(),userName,password,new BeanCallback<BaseJson<LoginResult>>(getContext()){
            @Override
            public void onResult(boolean success, BaseJson<LoginResult> response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(),response.forUser);
                    if (response.ret){

                        Cache.getInstance().setToken(response.data.tokenSession);
//                        Cache.getInstance().setPhone(phone);
                        Cache.getInstance().setUserId(response.data.companyId);
                        Cache.getInstance().setLoginType(2);
                        IntentHelper.openMainActivity(getContext());
                        finish();
                        App.setJPushAliasByUserId();
                    }
                }else {
                    T.showShort(getContext(),e.getMessage());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        IntentHelper.openLoginActivity(getContext());
        super.onBackPressed();
    }
}
