package com.cxd.khd.entity;

import java.util.List;

/**
 * Created by chenweiqi on 2017/6/16.
 */

public class SKSendDefaultInfoResult {
    public List<String> goodsType;// true array[string] 物品类型
    public List<String> weight;//  true array[string] 重量
    public List<String> volume;//  true array[string] 体积
    public List<ExpressCompany> express;// true array[object] 物流信息（一次全给了）
    public String servicePrice;// true string 服务费
}
