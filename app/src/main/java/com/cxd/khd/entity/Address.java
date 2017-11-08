package com.cxd.khd.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenweiqi on 2017/6/16.
 */

public class Address implements Parcelable{
    public String addressId ;//true string 地址id
    public String name;// true string 姓名
    public String mobile;// true string 联系方式
    public String address;// true string 地址
    public String isdefault;// true string 是否为默认地址（1代表是，0代表不是，默认0）
    public String latitude ;//true string 纬度
    public String longitude ;//true string 经度
    public String province = "";
    public String city = "";
    public String district = "";
    public String detail = "";

    public Address() {
    }


    protected Address(Parcel in) {
        addressId = in.readString();
        name = in.readString();
        mobile = in.readString();
        address = in.readString();
        isdefault = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        province = in.readString();
        city = in.readString();
        district = in.readString();
        detail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addressId);
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeString(address);
        dest.writeString(isdefault);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(district);
        dest.writeString(detail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
