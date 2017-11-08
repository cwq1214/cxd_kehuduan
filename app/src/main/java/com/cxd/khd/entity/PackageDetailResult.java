package com.cxd.khd.entity;

import java.util.List;

/**
 * Created by chenweiqi on 2017/6/21.
 */

public class PackageDetailResult {
    public String packageId ;//true string 包id
    public String packageNo ;//true string 包号码
    public String areaId ;//true string 地区编码
    public String remark ;//true string 备注
    public List<Track>track ;//true array[object] 跟踪信息

}
