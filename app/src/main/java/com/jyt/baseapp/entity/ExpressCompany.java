package com.jyt.baseapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenweiqi on 2017/6/16.
 */

public class ExpressCompany implements Parcelable {
    public String expressId;// true string 物流id
    public String eName;// true string 名称
    public String subsidy;// true string 补贴金额

    protected ExpressCompany(Parcel in) {
        expressId = in.readString();
        eName = in.readString();
        subsidy = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(expressId);
        dest.writeString(eName);
        dest.writeString(subsidy);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ExpressCompany> CREATOR = new Creator<ExpressCompany>() {
        @Override
        public ExpressCompany createFromParcel(Parcel in) {
            return new ExpressCompany(in);
        }

        @Override
        public ExpressCompany[] newArray(int size) {
            return new ExpressCompany[size];
        }
    };
}
