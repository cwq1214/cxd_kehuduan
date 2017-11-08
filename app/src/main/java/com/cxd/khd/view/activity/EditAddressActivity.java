package com.cxd.khd.view.activity;

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

import com.baidu.mapapi.model.LatLng;
import com.cxd.khd.R;
import com.cxd.khd.api.BeanCallback;
import com.cxd.khd.api.Http;
import com.cxd.khd.entity.Address;
import com.cxd.khd.entity.BaseJson;
import com.cxd.khd.util.IntentHelper;
import com.cxd.khd.util.SoftInputUtil;
import com.cxd.khd.util.T;
import com.cxd.khd.view.dialog.SelLocationDialog;

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
    @BindView(R.id.text_place)
    TextView textPlace;

    String longitude;
    String latitude;
    String name;
    String phone;
    String address;
    String type;
    String province;
    String city;
    String district;
    String detail;

    Address addressBean;
    SelLocationDialog dialog;
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
                province = addressBean.province;
                city = addressBean.city;
                district = addressBean.district;
                detail = addressBean.detail;
                if (TextUtils.isEmpty(addressBean.isdefault)||addressBean.isdefault.equals("0")){
                    cbSetDefault.setChecked(false);
                }else {
                    cbSetDefault.setChecked(true);
                }

                textPlace.setText(province+" "+city+" "+district);

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
                detail = s.toString();
            }
        });
        textAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    textAddress.setText("");
                    SoftInputUtil.showSoftKeyboard(getContext(),textAddress);
                }
            }
        });

        inputName.setText(name);
        inputContract.setText(phone);
        textAddress.setText(detail);



        cbSetDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setDefaultAddress();
            }
        });
    }

    @OnClick(R.id.btn_getLocation)
    public void onGetLocationClick() {
        if (addressBean!=null&&
                !TextUtils.isEmpty(addressBean.province)&&!TextUtils.isEmpty(addressBean.city)&&!TextUtils.isEmpty(addressBean.district)&&
                province.equals(addressBean.province)&&city.equals(addressBean.city)&&district.equals(addressBean.district)){
            IntentHelper.openSelAddressActivity(getContext(),latitude,longitude,province,city,district);
        }else {
            IntentHelper.openSelAddressActivity(getContext(),null,null,province,city,district);
        }

//        IntentHelper.openSelLocationActivity(getContext(), longitude, latitude);
    }

    @OnClick(R.id.btn_done)
    public void onDoneClick() {


        editAddress();
    }
    @OnClick(R.id.layout_selPlace)
    public void onSelPlaceClick(){
//        if (dialog==null) {
            dialog = new SelLocationDialog(getContext());
            dialog.setOnSelFinishCallback(new SelLocationDialog.OnSelFinishCallback() {
                @Override
                public void onSelFinish(final String province, final String city, final String district) {
                    EditAddressActivity.this.province = province;
                    EditAddressActivity.this.city = city;
                    EditAddressActivity.this.district = district;
                    EditAddressActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textPlace.setText(province+" "+city+" "+district);
                        }
                    });
                }
            });
//        }
        dialog.setSelectLocation(province,city,district);

        dialog.show();
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

    private void editAddress() {

        if (TextUtils.isEmpty(province)||TextUtils.isEmpty(city)||TextUtils.isEmpty(district)){
            T.showShort(getContext(),"请先选址所在地");
            return;
        }
        if (TextUtils.isEmpty(latitude)
                ||TextUtils.isEmpty(longitude)) {
//            BDLocation bdLocation = App.getLocation();
//
//            if (bdLocation!=null) {
//                lat = App.getLocation().getLatitude() + "";
//                lon = App.getLocation().getLongitude() + "";
//            }else {
            latitude = "0";
            longitude = "0";
//            }
        }
        String id =  null;
        if (addressBean != null) {
            id = addressBean.addressId;
        }
        detail = textAddress.getText().toString();
        if (TextUtils.isEmpty(province)||TextUtils.isEmpty(district)||TextUtils.isEmpty(detail)||TextUtils.isEmpty(city)){
            return;
        }

        address = province+city+district+detail;
        Http.addOrUpdateSKAddress(getContext(),id ,name, phone, address,longitude, latitude,province,city,district,detail, new BeanCallback<BaseJson>(getContext()) {
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
