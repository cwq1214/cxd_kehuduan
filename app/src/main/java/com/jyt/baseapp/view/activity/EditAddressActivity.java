package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.jyt.baseapp.App;
import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Http;
import com.jyt.baseapp.entity.Address;
import com.jyt.baseapp.entity.BaseJson;
import com.jyt.baseapp.util.IntentHelper;
import com.jyt.baseapp.util.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/6/5.
 */

public class EditAddressActivity extends BaseActivity {
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_contract)
    EditText inputContract;
    @BindView(R.id.text_address)
    EditText textAddress;
    @BindView(R.id.layout_selAddress)
    RelativeLayout layoutSelAddress;
    @BindView(R.id.btn_getLocation)
    ImageView btnGetLocation;
    @BindView(R.id.btn_done)
    TextView btnDone;
    @BindView(R.id.cb_setDefault)
    CheckBox cbSetDefault;
    @BindView(R.id.layout_setdefault)
    RelativeLayout layoutSetdefault;


    String longitude;
    String latitude;
    String name;
    String phone;
    String address;
    String type;

    Address addressBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        if (intent != null) {
            addressBean = intent.getParcelableExtra(IntentHelper.KEY_ADDRESS);
            if (addressBean!=null) {
                name = addressBean.name;
                phone = addressBean.mobile;
                address = addressBean.address;
                longitude = addressBean.longitude;
                latitude = addressBean.latitude;
                if (TextUtils.isEmpty(addressBean.isdefault)||addressBean.isdefault.equals("0")){
                    cbSetDefault.setChecked(false);
                }else {
                    cbSetDefault.setChecked(true);
                }

                btnFunction.setText("删除");
            }
        }

        layoutSetdefault.setVisibility(addressBean==null?View.GONE:View.VISIBLE);


        inputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = s.toString();
            }
        });

        inputContract.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = s.toString();
            }
        });

        textAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                address = s.toString();
            }
        });


        inputName.setText(name);
        inputContract.setText(phone);
        textAddress.setText(address);



        cbSetDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setDefaultAddress();
            }
        });
    }

    @OnClick(R.id.btn_getLocation)
    public void onGetLocationClick() {
        IntentHelper.openSelLocationActivity(getContext(), longitude, latitude);
    }

    @OnClick(R.id.btn_done)
    public void onDoneClick() {


        editAddress(type, name, address, phone, latitude, longitude);
    }

    @Override
    public void onFunctionClick() {
        deleteAddress();
    }

    private void setResultAndFinish() {
        if (addressBean==null){
            addressBean = new Address();
        }
        addressBean.address = address;
        addressBean.name = name;
        addressBean.mobile = phone;
        addressBean.latitude = latitude;
        addressBean.longitude = longitude;

        Intent intent = new Intent();
        intent.putExtra(IntentHelper.KEY_ADDRESS, addressBean);

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentHelper.REQUIRE_CODE_SEL_LOCATION && resultCode == RESULT_OK) {
            String address = data.getStringExtra(IntentHelper.KEY_ADDRESS);
            LatLng latLng = data.getParcelableExtra(IntentHelper.KEY_LATLNG);
            latitude = latLng.latitude + "";
            longitude = latLng.longitude + "";

            textAddress.setText(address);
        }
    }

    private void editAddress(String type, String name, String address, String phone, String lat, String lon) {


        if (TextUtils.isEmpty(lat)
                ||TextUtils.isEmpty(lon)) {
            BDLocation bdLocation = App.getLocation();

            if (bdLocation!=null) {
                lat = App.getLocation().getLatitude() + "";
                lon = App.getLocation().getLongitude() + "";
            }else {
                lat = "0";
                lon = "0";
            }
        }
        String id =  null;
        if (addressBean != null) {
            id = addressBean.addressId;
        }
        Http.addOrUpdateSKAddress(getContext(),id ,name, phone, address, lat, lon, new BeanCallback<BaseJson>(getContext()) {
            @Override
            public void onResponse(BaseJson response, int id) {
                super.onResponse(response,id);

                T.showShort(getContext(),response.forUser);
                if (response.ret){
//                    setResultAndFinish();
                    finish();
                }
            }
        });
    }


    private void setDefaultAddress(){
        Http.setSKDefaultAddress(getContext(), addressBean.addressId, cbSetDefault.isChecked() ? "1" : "0", new BeanCallback<BaseJson>(getContext(),false) {
            @Override
            public void onResult(boolean success, BaseJson response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(),response.forUser);
                    if (response.ret){
                        finish();
                    }
                }else {
                    T.showShort(getContext(),e.getMessage());
                }
            }
        });
    }

    private void deleteAddress(){
        Http.deleteAddress(getContext(), addressBean.addressId, new BeanCallback<BaseJson>(getContext()) {
            @Override
            public void onResult(boolean success, BaseJson response, Exception e) {
                super.onResult(success, response, e);
                if (success){
                    T.showShort(getContext(),response.forUser);
                    if (response.ret){
                        finish();
                    }
                }else {
                    T.showShort(getContext(),e.getMessage());

                }
            }
        });
    }
}
