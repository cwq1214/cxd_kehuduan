package com.jyt.baseapp.entity;

/**
 * Created by chenweiqi on 2017/6/19.
 */

public class SKOrderDetailResult {
    public String orderId;// true string 订单id
    public String userId;// true string 下单用户id
    public String orderNo;// true string 订单号
    public String receiveName;// true string 收件人
    public String receiveMobile;// true string 收件人联系方式
    public String receiveAddress;// true string 收件地址
    public String sendName;// true string 寄件人
    public String sendMobile;// true string 寄件人联系方式
    public String sendAddress;// true string 寄件人地址
    public String goodsType;// true string 物品类型
    public String value;// true string 声明价值
    public String weight;// true string 重量
    public String volume;// true string 体积
    public String payType;// true string 支付方式，1代表微信，2代表支付宝
    public String expressCompany;// true string 物流公司名字
    public String insured;// true string 是否保价，0代表不，1代表保
    public String insuredPrice;// true string 保价价格
    public String servicePrice;// true string 服务费
    public String price;// true string 运费
    public String receiveState;// true string 1已经给了服务费，待收件，2待支付运费，3正在配送至中转中心，4已完成，5订单被抢，等待服务，6达到中专中心
    public String receiveStateMsg;// true string 状态信息（例如：待收件，已完成）
    public String msg;// true string 配送状态信息（如：您已成功接单，请尽快上门）
    public String msgTime;// true string 配送状态时间
    public String totalPrice;// true string 总价
}
