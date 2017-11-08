package com.cxd.khd.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenweiqi on 2017/6/6.
 */

public class Order implements Parcelable{
    public String orderId ;//true string 订单id
    public String orderNo ;//true string 订单号
    public String receiveName;// true string 收件人姓名
    public String receiveMobile;// true string 收件人联系方式
    public String receiveAddress;// true 收件地址
    public String createdTime;// true string 创建时间
    public String trackingNo ;//true string 运单号
    public String expressCompany ;//true string 快递公司名称

    public int type;

    public String orderState;// true string 订单状态
    public String userMsg;// true string 寄件人-收件人

    protected Order(Parcel in) {
        orderId = in.readString();
        orderNo = in.readString();
        receiveName = in.readString();
        receiveMobile = in.readString();
        receiveAddress = in.readString();
        createdTime = in.readString();
        trackingNo = in.readString();
        expressCompany = in.readString();
        type = in.readInt();
        orderState = in.readString();
        userMsg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(orderNo);
        dest.writeString(receiveName);
        dest.writeString(receiveMobile);
        dest.writeString(receiveAddress);
        dest.writeString(createdTime);
        dest.writeString(trackingNo);
        dest.writeString(expressCompany);
        dest.writeInt(type);
        dest.writeString(orderState);
        dest.writeString(userMsg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
