package com.jyt.baseapp.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;

import com.jyt.baseapp.util.Cache;
import com.jyt.baseapp.util.ImageUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by chenweiqi on 2017/6/6.
 */

public class Http {
    public static final String DOMAIN = "http://119.23.66.37";

    private static final String SK_LOGIN = DOMAIN + "/logistics/user/login";
    private static final String DK_LOGIN = DOMAIN + "/logistics/company/login";
    private static final String GET_CODE = DOMAIN + "/logistics/user/login/get_code";
    private static final String SK_GET_BANNER = DOMAIN + "/logistics/user/home/marquee";
    private static final String SK_ADDRESS_LIST = DOMAIN + "/logistics/user/index/get_address";
    private static final String SK_ADD_OR_UPDATE_ADDRESS = DOMAIN + "/logistics/user/index/update_address";
    private static final String SK_SET_DEFAULT_ADDRESS = DOMAIN + "/logistics/user/index/set_default_address";
    private static final String SK_DELETE_ADDRESS = DOMAIN + "/logistics/user/index/delete_address";
    private static final String SK_GET_SEND_DEFAULT_INFO = DOMAIN + "/logistics/user/order/before_add";
    private static final String SK_COUNT_MONEY = DOMAIN + "/logistics/user/order/count_money";
    private static final String SK_ALI_PAY = DOMAIN + "/logistics/user/pay/alipay";
    private static final String SK_ORDER_LIST = DOMAIN + "/logistics/user/order";
    private static final String SK_ORDER_DETAIL = DOMAIN + "/logistics/user/order/detail";
    private static final String SK_TRACK_ORDER = DOMAIN + "/logistics/user/order/get_track";
    private static final String SK_HOME_MESSAGE = DOMAIN + "/logistics/user/message";
    private static final String SK_SYSTEM_MESSAGE = DOMAIN + "/logistics/user/message/get_order";
    private static final String SK_GET_USER_INFO = DOMAIN + "/logistics/user/";
    private static final String SK_SIGN = DOMAIN + "/logistics/user/index/sign";
    private static final String SK_MODIFY_AVATAR = DOMAIN + "/logistics/user/index/update_self";
    private static final String SK_CANCEL_ORDER = DOMAIN + "/logistics/user/order/cancel";
    private static final String SK_PAY_WITHOUT_MONTY = DOMAIN + "/logistics/user/pay/";

    private static final String BK_LOGIN = DOMAIN + "/logistics/company/login";
    private static final String BK_GET_BANNER = DOMAIN + "/logistics/company/home/marquee";
    private static final String BK_SUBMIT_SEND_ORDER  = DOMAIN + "/logistics/company/package/add";
    private static final String BK_PACK_DETAIL = DOMAIN + "/logistics/company/package/detail";
    private static final String BK_PACKAGE_LIST = DOMAIN + "/logistics/company/package/";
    private static final String BK_ORDER_LIST = DOMAIN + "/logistics/company/order";
    private static final String BK_ORDER_DETAIL = DOMAIN + "/logistics/company/order/detail";
    private static final String BK_MARK = DOMAIN + "/logistics/company/package/update_remark";
    private static final String BK_MESSAGE_ONE = DOMAIN + "/logistics/company/message";
    private static final String BK_MESSAGE_LIST = DOMAIN + "/logistics/company/message/get_order";
    private static final String BK_MODIFY_AVATAR = DOMAIN + "/logistics/company/index/update_self";
    private static final String BK_USER_INFO = DOMAIN + "/logistics/company/";
    private static final String BK_GET_DEFAULT_VALUE = DOMAIN + "/logistics/company/package/get_default_value";



    //bk获取默认订单接价值
    public static void bkGetDefaultValue(Context context,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(BK_GET_DEFAULT_VALUE)
                .build().execute(callback);
    }


    //sk 支付运费不用钱
    public static void payExpressPriceWithoutMoney(Context context,String orderId,String total_fee,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_PAY_WITHOUT_MONTY)
                .addParams("total_fee",total_fee)
                .addParams("orderId",orderId)
                .addParams("type",3+"")
                .build().execute(callback);
    }

    //sk 提交订单不用钱
    public static void submitOrderWithoutMoney(Context context,String sendName,String sendMobile,String sendAddress,String  receiveAddress,String receiveMobile,String receiveName,String value,String payType,String servicePrice,String price,String insured,String insuredPrice,String subsidy,String totalPrice,String volume,String weight,String goodsType,String expressCompany,String longitude,String latitude ,String expressId,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_PAY_WITHOUT_MONTY)
                .addParams("sendName",sendName)
                .addParams("sendMobile",sendMobile)
                .addParams("sendAddress",sendAddress)
                .addParams("receiveAddress",receiveAddress)
                .addParams("receiveMobile",receiveMobile)
                .addParams("receiveName",receiveName)
                .addParams("value",value)
                .addParams("payType",payType)
                .addParams("servicePrice",servicePrice)
                .addParams("price",price)
                .addParams("insured",insured)
                .addParams("insuredPrice",insuredPrice)
                .addParams("subsidy",subsidy)
                .addParams("totalPrice",totalPrice)
                .addParams("volume",volume)
                .addParams("weight",weight)
                .addParams("goodsType",goodsType)
                .addParams("expressCompany",expressCompany)
                .addParams("longitude",longitude)
                .addParams("latitude",latitude)
                .addParams("type","1")
                .addParams("expressId",expressId)
                .build().execute(callback);
    }

    //sk 取消订单
    public static void cancelOrder(Context context,String orderId,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_CANCEL_ORDER)
                .addParams("orderId",orderId).build().execute(callback);

    }

    //bk 用户信息
    public static void getBKUserInfo(Context context,BeanCallback callback){
        OkHttpUtils.get().addHeader("tokenSession", Cache.getInstance().getToken()).url(BK_USER_INFO).build().execute(callback);
    }

    //bk 修改头像
    public static void modifyBKAvatar(Context context,String imagePath,BeanCallback callback){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap =  ImageUtil.compress(imagePath,300);
        bitmap.compress(Bitmap.CompressFormat.JPEG,90,stream);

        byte[] bytes = stream.toByteArray();

        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(BK_MODIFY_AVATAR)
                .addParams("companyImg", Base64.encodeToString(bytes,Base64.NO_WRAP))
                .build().execute(callback);

    }

    //bk 消息列表
    public static void getBKMessageList(Context context,String lastId,BeanCallback callback){
        PostFormBuilder builder = OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken())
                .url(BK_MESSAGE_LIST);
        if (!TextUtils.isEmpty(lastId)){
            builder.addParams("lastId",lastId);
        }
        builder.build().execute(callback);
    }

    //bk 消息首页
    public static void getBKMessage(Context context,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken())
                .url(BK_MESSAGE_ONE).build().execute(callback);
    }

    //bk 修改包 备注
    public static void updatePackMark(Context context,String mark,String packageId, BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken())
                .addParams("remark",mark)
                .addParams("packageId",packageId)
                .url(BK_MARK).build().execute(callback);

    }

    //bk订单详情
    public static void getBKOrderDetail(Context context,String orderId,BeanCallback callback){
        OkHttpUtils.get().addHeader("tokenSession", Cache.getInstance().getToken()).addParams("orderId",orderId).url(BK_ORDER_DETAIL).build().execute(callback);
    }

    //bk 获取包列表
    public static void getBKPackageOrderList(Context context,String type,BeanCallback callback){
        OkHttpUtils.get().addHeader("tokenSession", Cache.getInstance().getToken()).url(BK_PACKAGE_LIST)
                .addParams("type",type)
                .build().execute(callback);
    }

    //bk 获取订单列表
    public static void getBKOrderList(Context context,String type , String packageNo,BeanCallback callback){
        GetBuilder getBuilder = OkHttpUtils.get().addHeader("tokenSession", Cache.getInstance().getToken()).url(BK_ORDER_LIST)
                .addParams("type",type);
        if (!TextUtils.isEmpty(packageNo)){
            getBuilder.addParams("packageNo",packageNo);
        }
        getBuilder.build().execute(callback);
    }

    //bk 包详情
    public static void BKPackageDetail(Context context,String packageId,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(BK_PACK_DETAIL)
                .addParams("packageId",packageId).build().execute(callback);
    }

    //bk 下单
    public static void BKSubmitSendOrder(Context context, String areaId, String packageNo, List<String> trackingNo,List<String> value,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(BK_SUBMIT_SEND_ORDER)
                .addParams("areaId",areaId)
                .addParams("packageNo",packageNo)
//                .addParams("trackingNo",Arrays.toString(trackingNo.toArray()))
                .addParams("trackingNo",new JSONArray(trackingNo).toString())
//                .addParams("value",Arrays.toString(value.toArray()))
                .addParams("value",new JSONArray(value).toString())
                .build().execute(callback);
    }

    //bk 首页轮播
    public static void getBKBanner(Context context,BeanCallback callback){
        OkHttpUtils.get().addHeader("tokenSession", Cache.getInstance().getToken()).url(BK_GET_BANNER).build().execute(callback);
    }
    //bk 登录
    public static void BKLogin(Context context,String username , String password ,BeanCallback callback){
        OkHttpUtils.post().url(BK_LOGIN).addParams("username",username).addParams("pwd",password).build().execute(callback);
    }

    //sk 修改头像
    public static void modifySKAvatar(Context context,String imagePath,BeanCallback callback){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = ImageUtil.compress(imagePath,300);
        bitmap.compress(Bitmap.CompressFormat.PNG,90,stream);

        byte[] bytes = stream.toByteArray();

        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_MODIFY_AVATAR)
                .addParams("userImg", Base64.encodeToString(bytes,Base64.NO_WRAP))
                .build().execute(callback);

    }

    //sk 签到
    public static void SKSign(Context context,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_SIGN).build().execute(callback);

    }

    //sk 个人中心
    public static void getSKUserInfo(Context context,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_GET_USER_INFO).build().execute(callback);

    }

    //sk 消息列表
    public static void getSKSystemMessageList(Context context ,String lastId, BeanCallback callback){
        PostFormBuilder builder = OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_SYSTEM_MESSAGE);
        if (!TextUtils.isEmpty(lastId)){
            builder.addParams("lastId",lastId);
        }
        builder.build().execute(callback);
    }

    //sk 消息主界面
    public static void getSKHomeMessage(Context context,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_HOME_MESSAGE).build().execute(callback);
    }

    //登录 散客
    public static void login(Context context, String mobile , String code,BeanCallback callback){
        OkHttpUtils.post().url(SK_LOGIN).addParams("mobile",mobile).addParams("code",code)
                .build().execute(callback);
    }
    //登录 大客户
    public static void DKlogin(Context context, String mobile , String code,BeanCallback callback){
        OkHttpUtils.post().url(DK_LOGIN).addParams("mobile",mobile).addParams("code",code)
                .build().execute(callback);
    }
    //获取验证码
    public static void getCode(Context context,String mobile,BeanCallback callback){
        OkHttpUtils.post().url(GET_CODE).addParams("mobile",mobile).build().execute(callback);
    }

    //sk 轮播图
    public static void getSKBanner(Context context ,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_GET_BANNER).build().execute(callback);

    }

    //sk 地址列表
    public static void getSKAddressList(Context context,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_ADDRESS_LIST).build().execute(callback);
    }

    //sk 添加地址
    public static void addOrUpdateSKAddress(Context context,String addressId,String name,String mobile,String address,String longitude,String latitude,BeanCallback callback){


        PostFormBuilder builder = OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_ADD_OR_UPDATE_ADDRESS);
        if (!TextUtils.isEmpty(addressId)){
            builder.addParams("addressId",addressId);
        }
        builder.addParams("name",name);
        builder.addParams("mobile",mobile);
        builder.addParams("address",address);
        builder.addParams("longitude",longitude);
        builder.addParams("latitude",latitude);

        builder.build().execute(callback);
    }

    //sk 设置默认地址
    public static void setSKDefaultAddress(Context context,String addressId,String isDefault,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_SET_DEFAULT_ADDRESS)
                .addParams("addressId",addressId)
                .addParams("isdefault",isDefault)
                .build().execute(callback);
    }

    //SK 删除地址
    public static void deleteAddress(Context context,String addressId,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_DELETE_ADDRESS)
                .addParams("addressId",addressId)
                .build().execute(callback);

    }
    //sk 获取下单前信息
    public static void getSKSendDefaultInfo(Context context,BeanCallback callback){
        OkHttpUtils.get().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_GET_SEND_DEFAULT_INFO)
                .build().execute(callback);
    }

    //sk 计算价格
    public static void SkCountMoney(Context context , String insured,String value,String payType , String volume ,String weight, String goodsType ,String expressId, BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_COUNT_MONEY)
                .addParams("insured",insured)
                .addParams("value",value)
                .addParams("payType",payType)
                .addParams("volume",volume)
                .addParams("weight",weight)
                .addParams("goodsType",goodsType)
                .addParams("expressId",expressId)
                .build().execute(callback);
    }

    //sk 提交订单
    public static void SKSubmitOrder(Context context,String sendName,String sendMobile,String sendAddress,String  receiveAddress,String receiveMobile,String receiveName,String value,String payType,String servicePrice,String price,String insured,String insuredPrice,String subsidy,String totalPrice,String volume,String weight,String goodsType,String expressCompany,String longitude,String latitude ,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_ALI_PAY)
                .addParams("sendName",sendName)
                .addParams("sendMobile",sendMobile)
                .addParams("sendAddress",sendAddress)
                .addParams("receiveAddress",receiveAddress)
                .addParams("receiveMobile",receiveMobile)
                .addParams("receiveName",receiveName)
                .addParams("value",value)
                .addParams("payType",payType)
                .addParams("servicePrice",servicePrice)
                .addParams("price",price)
                .addParams("insured",insured)
                .addParams("insuredPrice",insuredPrice)
                .addParams("subsidy",subsidy)
                .addParams("totalPrice",totalPrice)
                .addParams("volume",volume)
                .addParams("weight",weight)
                .addParams("goodsType",goodsType)
                .addParams("expressCompany",expressCompany)
                .addParams("longitude",longitude)
                .addParams("latitude",latitude)
                .addParams("type","1")
                .build().execute(callback);
    }

    //sk 支付运费
    public static void payExpressPrice(Context context,String orderId,String total_fee,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_ALI_PAY)
                .addParams("total_fee",total_fee)
                .addParams("orderId",orderId)
                .addParams("type",3+"")
                .build().execute(callback);
    }

    //sk 订单列表
    public static void getSKOrderList(Context context,int type,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_ORDER_LIST)
                .addParams("type",type+"")
                .build().execute(callback);
    }
    //sk 订单详情
    public static void getSKOrderDetail(Context context,String orderId,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_ORDER_DETAIL)
                .addParams("orderId",orderId)
                .build().execute(callback);
    }

    //sk 获取订单跟踪信息
    public static void getSKTrackOrder(Context context,String orderId,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SK_TRACK_ORDER).addParams("orderId",orderId).build().execute(callback);
    }

/*


    //获取轮播图
    public static void setGetBanner(Context context,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(GET_BANNER).build().execute(callback);
    }

    //上下班
    public static void setChangeWorkState(Context context,boolean isWorking, BeanCallback callback){
        String jobState = isWorking?"1":"0";
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(CHANGE_WORK_STATE).addParams("jobState",jobState).build().execute(callback);
    }

    //获取二维码
    public static void getQrCode(Context context, final BeanCallback callback){
        Glide.with(context).load(GET_QR_CODE).downloadOnly(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                callback.onResponse(resource,0);
            }
        });
    }

    //派件列表
    //1代表派件中，2代表派件失败，3代表已完成
    public static void getSendList(Context context,String type,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(LIST_SEND).addParams("type",type).build().execute(callback);
    }

    //收件列表
    //1代表待收件（已抢单），2代表进行中，3代表已完成，4代表取消
    public static void getPickUpList(Context context,String type ,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(LIST_PICK_UP).addParams("type",type).build().execute(callback);
    }

    //派件 订单详情
    public static void getSendOrderDetail(Context context,String orderId,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SEND_ORDER_DETAIL).addParams("orderId",orderId).build().execute(callback);
    }

    //收件 订单详情
    public static void getReceiveOrderDetail(Context context,String orderId,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(RECEIVE_ORDER_DETAIL).addParams("orderId",orderId).build().execute(callback);
    }

    //订单追踪
    public static void getTrackOrder(Context context,String orderId,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(TRACK_ORDER).addParams("orderId",orderId).build().execute(callback);
    }

    //修改地址
    public static void setUpdateAddress(Context context,String type,String name ,String mobile,String address,String orderId,String latitude,String longitude,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(UPDATE_ADDRESS)
                .addParams("type",type)
                .addParams("name",name)
                .addParams("mobile",mobile)
                .addParams("address",address)
                .addParams("orderId",orderId)
                .addParams("latitude",latitude)
                .addParams("longitude",longitude)
                .build().execute(callback);
    }

    //计算价钱
    public static void countMoney(Context context, String orderId,String insured,String payType,String volume,String weight,String goodsType,String value,String otherPrice , BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(COUNT_MONEY)
                .addParams("orderId",orderId)
                .addParams("insured",insured)
                .addParams("payType",payType)
                .addParams("volume",volume)
                .addParams("weight",weight)
                .addParams("goodsType",goodsType)
//                .addParams("value",value)
//                .addParams("otherPrice",otherPrice)
                .build().execute(callback);
    }

    //派件 成功 失败原因
    public static void getSendSelItemList(Context context,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SEND_SEL_ITEM).build().execute(callback);
    }

    //收件 确认信息
    public static void sendConfirm(Context context, String orderId,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(RECEIVE_CONFIRM).addParams("orderId",orderId).build().execute(callback);
    }

    //派件 确定
    public static void finishSendOrder(Context context,String orderId,String status,String failureReason,String signer,String packageNo,String idCard,String idCardImg , BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SEND_FINISH)
                .addParams("orderId",orderId)
                .addParams("status",status)
                .addParams("failureReason",failureReason)
                .addParams("signer",signer)
                .addParams("packageNo",packageNo)
                .addParams("idCard",idCard)
                .addParams("idCardImg",idCardImg)
                .build().execute(callback);
    }

    //获取个人信息
    public static void getSelfInfo(Context context,BeanCallback callback){
        OkHttpUtils.post().url(GET_SELF_INFO).addHeader("tokenSession", Cache.getInstance().getToken())
                .build().execute(callback);
    }

    //获取保证金列表
    public static void getDepositList(Context context,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(GET_DEPOSIT_LIST).build().execute(callback);
    }

    //购买保证金前 检查
    public static void checkDeposit(Context context,String depositId,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(IS_HAVE_DEPOSIT)
                .addParams("depositId",depositId)
                .build().execute(callback);
    }

    //获取银行卡列表
    public static void getBankCardList(Context context,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(GET_BANK_CARD_LIST)
                .build().execute(callback);
    }

    //绑定银行卡
    public static void bindCard(Context context,String bankCardId,String userName , String bankName ,String cardNum , BeanCallback callback){
        PostFormBuilder builder = OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(BIND_CARD)
                .addParams("name",userName)
                .addParams("bankNumber",cardNum)
                .addParams("openBank",bankName);
                if (!TextUtils.isEmpty(bankCardId)){
                    builder.addParams("bankId",bankCardId);
                }

        builder.build().execute(callback);
    }

    //消费明细
    public static void paymentsDetail(Context context,String lastId,BeanCallback callback){
        OkHttpUtils.get().addHeader("tokenSession", Cache.getInstance().getToken()).url(PAYMENT_DETAIL)
                .addParams("lastId",lastId)
                .build().execute(callback);
    }

    //聊天主界面
    public static void messageBox(Context context, BeanCallback callback){
        OkHttpUtils.get().addHeader("tokenSession", Cache.getInstance().getToken()).url(MSG_BOX_HOME).build().execute(callback);
    }

    //获取系统信息
    public static void getSystemMessageList(Context context,String lastId,BeanCallback callback){
        GetBuilder builder = OkHttpUtils.get().addHeader("tokenSession", Cache.getInstance().getToken()).url(MSG_LIST_SYSTEM);

        if (!TextUtils.isEmpty(lastId))
            builder.addParams("lastId",lastId);

        builder.build().execute(callback);
    }

    //获取订单信息
    public static void getOrderMessageList(Context context,String lastId,BeanCallback callback){
        GetBuilder builder = OkHttpUtils.get().addHeader("tokenSession", Cache.getInstance().getToken()).url(MSG_LIST_ORDER);

        if (!TextUtils.isEmpty(lastId))
            builder.addParams("lastId",lastId);

        builder.build().execute(callback);
    }

    //身份证识别
    public static void ocrIdCard(Bitmap bitmap,String faceOrBack,BeanCallback callback){

        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos);//参数100表示不压缩
        byte[] bytes=bos.toByteArray();
        String base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
        String content = "{\"inputs\": [ {\"image\": {\"dataType\": 50,\"dataValue\": \""+base64+"\"},\"configure\": {\"dataType\": 50,\"dataValue\": \"{\\\"side\\\":\\\""+faceOrBack+"\\\"}\"}}]}";
        String appcode = "7d325476734a44dcae9afeabfc7cee2e";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        OkHttpUtils.postString().url("http://dm-51.data.aliyun.com/rest/160601/ocr/ocr_idcard.json")
                .headers(headers)
                .content(content)
                .build().execute(callback);
    }

    //删除银行卡
    public static void deleteBankCard(Context context,String bankCardId,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(DELETE_BAND_CARD)
                .addParams("bankId",bankCardId)
                .build().execute(callback);
    }

    //提现申请
    public static void postBalance(Context context,String bankCardId,String cash,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(POST_BALANCE)
                .addParams("bankId",bankCardId)
                .addParams("cash",cash)
                .build().execute(callback);
    }

    //根据运单号查询订单id
    public static void getOrderIdByOrderNo(Context context , String packageNo ,BeanCallback callback){
        OkHttpUtils.post().addHeader("tokenSession", Cache.getInstance().getToken()).url(SEND_SEL_ITEM)
                .addParams("packageNo",packageNo)
                .build().execute(callback);
    }

*/
}
