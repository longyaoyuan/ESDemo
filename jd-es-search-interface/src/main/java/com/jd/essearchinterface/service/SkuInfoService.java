package com.jd.essearchinterface.service;


import com.jd.essearchapi.entity.SkuInfo;

public interface SkuInfoService {
    //把mysql的数据上传到es
    public void saveSkuInfo(SkuInfo skuInfo);
}
