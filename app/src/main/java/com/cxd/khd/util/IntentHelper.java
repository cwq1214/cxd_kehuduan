package com.cxd.khd.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.cxd.khd.entity.Address;
import com.cxd.khd.entity.ExpressCompany;
import com.cxd.khd.entity.OrderTrackResult;
import com.cxd.khd.view.activity.AddressListActivity;
import com.cxd.khd.view.activity.BKLoginActivity;
import com.cxd.khd.view.activity.BKOrderDetailActivity;
import com.cxd.khd.view.activity.BKOrderListActivity;
import com.cxd.khd.view.activity.BKPackOrderActivity;
import com.cxd.khd.view.activity.BKSendOrderActivity;
import com.cxd.khd.view.activity.EditAddressActivity;
import com.cxd.khd.view.activity.ExpressCompanyListActivity;
import com.cxd.khd.view.activity.GoodsDetailActivity;
import com.cxd.khd.view.activity.GuidePageActivity;
import com.cxd.khd.view.activity.LoginActivity;
import com.cxd.khd.view.activity.MainActivity;
import com.cxd.khd.view.activity.MessageBoxActivity;
import com.cxd.khd.view.activity.MessageListActivity;
import com.cxd.khd.view.activity.OrderDetailActivity;
import com.cxd.khd.view.activity.PackDetailActivity;
import com.cxd.khd.view.activity.PayActivity;
import com.cxd.khd.view.activity.SKOrderListActivity;
import com.cxd.khd.view.activity.SKPersonalCenterActivity;
import com.cxd.khd.view.activity.SKSendActivity;
import com.cxd.khd.view.activity.SearchLocationActivity;
import com.cxd.khd.view.activity.SelLocationActivity;
import com.cxd.khd.view.activity.SelLocationActivity2;
import com.cxd.khd.view.activity.TrackOrderActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenweiqi on 2017/6/1.
 */

public class IntentHelper {
    public static final String KEY_POIINFO = "key_poiinfo";
    public static final String KEY_LONGITUDE = "key_longitude";
    public static final String KEY_LATITUDE = "key_latitude";
    public static final String KEY_TYPE = "key_type";
    public static final String KEY_ORDER_ID = "key_order_id";
    public static final String KEY_NAME = "key_name";
    public static final String KEY_PHONE = "key_phone";
    public static final String KEY_ADDRESS = "key_address";
    public static final String KEY_LATLNG = "key_latlng";
    public static final String KEY_ORDER_TRACK_RESULT = "key_order_track_result";
    public static final String KEY_LIST_TYPE = "key_list_type";
    public static final String KEY_WEIGHT = "key_weight";
    public static final String KEY_LIST_WEIGHT = "key_list_weight";
    public static final String KEY_VOLUME = "key_volume";
    public static final String KEY_LIST_VOLUME = "key_list_volume";
    public static final String KEY_VALUE = "key_value";
    public static final String KEY_COMPANY = "key_company";
    public static final String KEY_COMPANIES = "key_companies";
    public static final String KEY_PAY_TYPE = "key_pay_type";
    public static final String KEY_KEEP_VALUE = "key_keep_value";
    public static final String KEY_COST = "key_cost";
    public static final String KEY_CASH  = "key_cash";
    public static final String KEY_DEPOSIT_ID = "key_deposit_id";
    public static final String KEY_BAND_CARD = "key_band_card";
    public static final String KEY_ORDER = "key_order";
    public static final String KEY_NO_CLOSE = "key_no_close";
    public static final String KEY_EXIT = "key_exit";
    public static final String KEY_PACKAGE_ID = "key_package_id";
    public static final String KEY_STATE = "key_state";
    public static final String KEY_ORDER_INFO = "key_order_info";
    public static final String KEY_AUTO_PAY = "key_auto_pay";
    public static final String KEY_MONEY = "key_money";
    public static final String KEY_PAY_RESULT = "key_pay_result";
    public static final String KEY_TITLE = "key_title";
    public static final String KEY_EDIT_ADDRESS = "key_edit_address";
    public static final String KEY_PROVINCE = "KEY_PROVINCE";
    public static final String KEY_CITY = "KEY_CITY";
    public static final String KEY_DISTRICT = "KEY_DISTRICT";
    public static final String KEY_DETAIL = "KEY_DETAIL";
    public static final String KEY_PARTNERID = "KEY_PARTNERID";
    public static final String KEY_PREPAYID = "KEY_PREPAYID";
    public static final String KEY_TIMESTAMP = "KEY_TIMESTAMP";
    public static final String KEY_SIGN = "KEY_SIGN";
    public static final String KEY_PACKAGEVALUE = "KEY_PACKAGEVALUE";
    public static final String KEY_NONCESTR = "KEY_NONCESTR";



    public static final int REQUIRE_CODE_SEARCH_LOCATION = 0;
    public static final int REQUIRE_CODE_SEL_LOCATION = 1;
    public static final int REQUIRE_CODE_EDIT_SEND_ADDRESS = 2;
    public static final int REQUIRE_CODE_EDIT_RECEIVE_ADDRESS = 3;
    public static final int REQUIRE_CODE_EDIT_ORDER_PAY_INFO = 4;
    public static final int REQUIRE_CODE_EDIT_GOODS_INFO = 5;
    public static final int REQUIRE_CODE_TAKE_PHOTO = 6;
    public static final int REQUIRE_CODE_SEL_BAND_CARD = 7;
    public static final int REQUIRE_CODE_SEL_SENDER_ADDRESS = 8;
    public static final int REQUIRE_CODE_SEL_RECEIVEER_ADDRESS = 9;
    public static final int REQUIRE_PAY = 10;


    //
    public static void openSystemBrowser(Context context,String url){
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            context.startActivity(intent);
        }catch (Exception e){

        }

    }

    //支付宝 支付 并 返回结果
    public static void openPayActivityForResult_ALI(Context context,String sign,String name,String money,boolean autoPay){
        Intent intent = getIntent(context, PayActivity.class);
        intent.putExtra(IntentHelper.KEY_ORDER_INFO,sign);
        intent.putExtra(IntentHelper.KEY_NAME,name);
        intent.putExtra(IntentHelper.KEY_MONEY,money);
        intent.putExtra(IntentHelper.KEY_AUTO_PAY,autoPay);
        intent.putExtra(IntentHelper.KEY_PAY_TYPE,PayActivity.AUTO_PAY_ALI_PAY);
        if (context instanceof Activity){
            ((Activity) context).startActivityForResult(intent,REQUIRE_PAY);
        }
    }

    //微信 支付 并 返回结果
    public static void openPayActivityForResult_WEIXIN(Context context,String partnerId,String prepayId,String timeStamp,String sign,String packageValue,String nonceStr,String name,String money,boolean autoPay){
        Intent intent = getIntent(context, PayActivity.class);
        intent.putExtra(IntentHelper.KEY_PARTNERID,partnerId);
        intent.putExtra(IntentHelper.KEY_PREPAYID,prepayId);
        intent.putExtra(IntentHelper.KEY_TIMESTAMP,timeStamp);
        intent.putExtra(IntentHelper.KEY_SIGN,sign);
        intent.putExtra(IntentHelper.KEY_PACKAGEVALUE,packageValue);
        intent.putExtra(IntentHelper.KEY_NONCESTR,nonceStr);
        intent.putExtra(IntentHelper.KEY_NAME,name);
        intent.putExtra(IntentHelper.KEY_MONEY,money);
        intent.putExtra(IntentHelper.KEY_AUTO_PAY,autoPay);
        intent.putExtra(IntentHelper.KEY_PAY_TYPE,PayActivity.AUTO_PAY_WXP_AY);
        if (context instanceof Activity){
            ((Activity) context).startActivityForResult(intent,REQUIRE_PAY);
        }
    }

    //bk 包 订单列表
    public static void openBKPackOrderListActivity(Context context,String packNo){
        Intent intent = getIntent(context, BKPackOrderActivity.class);
        intent.putExtra(IntentHelper.KEY_PACKAGE_ID,packNo);
        context.startActivity(intent);
    }

    //bk 订单详情
    public static void openBKOrderDetailActivity(Context context,String orderId,String state){
        Intent intent = getIntent(context, BKOrderDetailActivity.class);
        intent.putExtra(IntentHelper.KEY_ORDER_ID,orderId);
        intent.putExtra(IntentHelper.KEY_STATE,state);
        context.startActivity(intent);
    }

    //bk 包列表
    public static void openBKPackageAndOrderListActivity(Context context){
        Intent intent = getIntent(context, BKOrderListActivity.class);
        context.startActivity(intent);
    }

    //包详情
    public static void openPackDetailActivity(Context context,String packageId){
        Intent intent = getIntent(context, PackDetailActivity.class);
        intent.putExtra(IntentHelper.KEY_PACKAGE_ID,packageId);
        context.startActivity(intent);
    }

    //批量下单
    public static void openBKSendOrderActivity(Context context){
        Intent intent = getIntent(context, BKSendOrderActivity.class);
        context.startActivity(intent);
    }

    //打开系统消息列表
    public static void openSystemMessageListActivity(Context context){
        Intent intent = getIntent(context, MessageListActivity.class);
        intent.putExtra(KEY_TYPE,0);
        context.startActivity(intent);
    }

    //打开订单消息列表
    public static void openOrderMessageListActivity(Context context){
        Intent intent = getIntent(context, MessageListActivity.class);
        intent.putExtra(KEY_TYPE,1);
        context.startActivity(intent);
    }
    //主界面
    public static void openMainActivity(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
    }

    //登录
    public static void openLoginActivity(Context context){
        Intent intent = getIntent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Cache.getInstance().setLoginType(-1);
        if(context instanceof Activity) {
            context.startActivity(intent);
        }

    }
    //bk 登录
    public static void openBKLoginActivity(Context context){
        Intent intent = getIntent(context, BKLoginActivity.class);
        context.startActivity(intent);

    }

    //关闭app
    public static void exit(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra(KEY_EXIT,true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    //散客寄件
    public static void openSKSendActivity(Context context){
        context.startActivity(getIntent(context, SKSendActivity.class));
    }
    //散客订单详情
    public static void openSKOrderDetailActivity(Context context, String orderId){
        Intent intent = getIntent(context,SKSendActivity.class);
        intent.putExtra(IntentHelper.KEY_ORDER_ID,orderId);
        intent.putExtra(IntentHelper.KEY_TITLE,"订单详情");
        context.startActivity(intent);
    }


    //散客订单列表
    public static void openSKOrderListActivity(Context context){
        context.startActivity(getIntent(context, SKOrderListActivity.class));

    }

    //消息列表
    public static void openMessageBox(Context context){
        context.startActivity(getIntent(context, MessageBoxActivity.class));
    }

    //散客个人中心
    public static void openSKPersonalCenter(Context context){
        context.startActivity(getIntent(context, SKPersonalCenterActivity.class));

    }
    //寄件人 地址列表
    public static void openSenderAddressListActivityForResult(Context context){
        Intent intent = getIntent(context, AddressListActivity.class);
        if (context instanceof Activity)
            ((Activity) context).startActivityForResult(intent,REQUIRE_CODE_SEL_SENDER_ADDRESS);
    }
    //收件人 地址列表
    public static void openReceiverAddressListActivityForResult(Context context){
        Intent intent = getIntent(context, AddressListActivity.class);
        if (context instanceof Activity)
            ((Activity) context).startActivityForResult(intent,REQUIRE_CODE_SEL_RECEIVEER_ADDRESS);
    }
    public static void openSKAddressListActivity(Context context){
        Intent intent = getIntent(context, AddressListActivity.class);
        intent.putExtra(KEY_NO_CLOSE,true);
        context.startActivity(intent);
    }

    //选择地址
    public static void openSearchLocationActivityForResult(Context context, LatLng currentLatlng) {
        Intent intent = getIntent(context, SearchLocationActivity.class);
        intent.putExtra(KEY_LATLNG,currentLatlng);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, REQUIRE_CODE_SEARCH_LOCATION);
        }
    }
    //选择地址
    public static void openSelLocationActivity(Context context,String longitude,String latitude){
        Intent intent = new Intent(context, SelLocationActivity.class);
        intent.putExtra(KEY_LONGITUDE,longitude);
        intent.putExtra(KEY_LATITUDE,latitude);
        if (context instanceof Activity){
            ((Activity) context).startActivityForResult(intent,REQUIRE_CODE_SEL_LOCATION);
        }
    }

    //修改地址
    public static void openEditAddressActivity(Context context, Address address){
        Intent intent = getIntent(context, EditAddressActivity.class);
        intent.putExtra(IntentHelper.KEY_ADDRESS,address);
        context.startActivity(intent);
    }

    //新增地址
    public static void openAddNewAddressActivity(Context context){
        Intent intent = getIntent(context, EditAddressActivity.class);

        context.startActivity(intent);

    }

    //拍照
    public static void openTakePhotoActivityForResult(Context context){
        String state = Environment.getExternalStorageState(); //拿到sdcard是否可用的状态码
        if (state.equals(Environment.MEDIA_MOUNTED)){   //如果可用
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            if (context instanceof  Activity) {
                ((Activity) context).startActivityForResult(intent, REQUIRE_CODE_TAKE_PHOTO);
            }
        }else {
            Toast.makeText(context,"sdcard不可用",Toast.LENGTH_SHORT).show();
        }
    }
    //拍照
    public static void openTakePhotoActivityForResult(Context context,int requestCode){
        String state = Environment.getExternalStorageState(); //拿到sdcard是否可用的状态码
        if (state.equals(Environment.MEDIA_MOUNTED)){   //如果可用
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            if (context instanceof  Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            }
        }else {
            Toast.makeText(context,"sdcard不可用",Toast.LENGTH_SHORT).show();
        }
    }


    //选择照片
    public static void openPickPhotoActivityForResult(Context context,int requestCode){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intent.setType("image/*");

        if(context instanceof Activity){
            ((Activity) context).startActivityForResult(intent,requestCode);
        }
    }

    //选择照片后获取文件
    public static Uri afterPickPhoto(Intent data){
        return data.getData();
    }


    //物品信息
    public static void openGoodsDetailActivity(Context context , String currentType, List type,String currentWeight,List weight,String currentVolume,List volume,String currentValue){
        Intent intent = getIntent(context, GoodsDetailActivity.class);
        intent.putExtra(KEY_TYPE,currentType);
        intent.putCharSequenceArrayListExtra(KEY_LIST_TYPE, (ArrayList<CharSequence>) type);
        intent.putExtra(KEY_WEIGHT,currentWeight);
        intent.putCharSequenceArrayListExtra(KEY_LIST_WEIGHT, (ArrayList<CharSequence>) weight);
        intent.putExtra(KEY_VOLUME,currentVolume);
        intent.putCharSequenceArrayListExtra(KEY_LIST_VOLUME, (ArrayList<CharSequence>) volume);
        intent.putExtra(KEY_VALUE,currentValue);
        if (context instanceof Activity)
            ((Activity) context).startActivityForResult(intent,REQUIRE_CODE_EDIT_GOODS_INFO);
    }

    //快递信息
    public static void openOrderDetailActivityForResult(Context context, ExpressCompany company, List<ExpressCompany> companies, String payType, String keepValue, String cost){
        Intent intent = getIntent(context, OrderDetailActivity.class);
        intent.putExtra(KEY_COMPANY,company);
        intent.putExtra(KEY_PAY_TYPE,payType);
        intent.putParcelableArrayListExtra(KEY_COMPANIES, (ArrayList<? extends Parcelable>) companies);
        intent.putExtra(KEY_KEEP_VALUE,keepValue);
        intent.putExtra(KEY_COST,cost);
        if (context instanceof Activity)
            ((Activity) context).startActivityForResult(intent,REQUIRE_CODE_EDIT_ORDER_PAY_INFO);
    }

    //快递列表
    public static void openExpressCompanyListActivityForResult(Context context,List<ExpressCompany> companies){
        Intent intent = getIntent(context, ExpressCompanyListActivity.class);
        intent.putParcelableArrayListExtra(KEY_COMPANIES, (ArrayList<? extends Parcelable>) companies);
        if (context instanceof Activity)
            ((Activity) context).startActivityForResult(intent,REQUIRE_CODE_EDIT_ORDER_PAY_INFO);
    }

    //订单跟踪
    public static void openTrackOrderActivity(Context context, OrderTrackResult result){
        Intent intent = getIntent(context, TrackOrderActivity.class);
        intent.putExtra(KEY_ORDER_TRACK_RESULT,result);
        context.startActivity(intent);
    }

    //选址地址
    public static void openSelAddressActivity(Context context,String lat,String lon,String province,String city,String district){
        Intent intent = getIntent(context, SelLocationActivity2.class);
        intent.putExtra(IntentHelper.KEY_LATITUDE,lat);
        intent.putExtra(IntentHelper.KEY_LONGITUDE,lon);
        intent.putExtra(IntentHelper.KEY_PROVINCE,province);
        intent.putExtra(IntentHelper.KEY_CITY,city);
        intent.putExtra(IntentHelper.KEY_DISTRICT,district);
        intent.putExtra(IntentHelper.KEY_EDIT_ADDRESS,true);
        ((Activity) context).startActivityForResult(intent,IntentHelper.REQUIRE_CODE_SEL_LOCATION);
    }

    private static Intent getIntent(Context context,Class activity){
        return new Intent(context,activity);
    }


    public static void openGuideActivity(Context context) {
        context.startActivity(getIntent(context, GuidePageActivity.class));
    }
}

