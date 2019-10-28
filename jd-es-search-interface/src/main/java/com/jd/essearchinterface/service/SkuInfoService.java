package com.jd.essearchinterface.service;


import com.jd.essearchapi.entity.SkuInfo;
import com.jd.essearchapi.entity.SkuInfoParams;
import com.jd.essearchapi.entity.SkuInfoResult;

public interface SkuInfoService {
    //把mysql的数据上传到es
    public void saveSkuInfo(SkuInfo skuInfo);

    //查询数据接口
    public SkuInfoResult skuSearch(SkuInfoParams skuInfoParams);

}
