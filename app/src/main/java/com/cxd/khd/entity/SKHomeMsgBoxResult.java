package com.cxd.khd.entity;

/**
 * Created by chenweiqi on 2017/6/19.
 */

public class SKHomeMsgBoxResult {
    public Order order;
    public String countOrder;



    public class Order{
        public String content ;//true string 内容
        public String createdTime ;//true string 时间
    }
}
