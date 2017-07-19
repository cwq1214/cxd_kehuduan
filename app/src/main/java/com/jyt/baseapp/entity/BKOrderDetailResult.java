package com.jyt.baseapp.entity;

import java.util.List;

/**
 * Created by chenweiqi on 2017/6/22.
 */

public class BKOrderDetailResult {
    public String partnerName ;//true string 合伙人姓名
    public String mobile ;//true string 合伙人联系方式
    public String trackingNo ;//true string 运单号
    public String signer ;//true string 签收人
    public String failureReason;// true string 失败原因
    public String orderNo ;//true string 订单号
    public List<Track> track;// true array[object]

}
