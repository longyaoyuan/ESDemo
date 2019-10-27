package com.jd.essearchinterface.service;


import com.jd.essearchapi.entity.SkuInfo;

public interface ManagerService {

    //从mysql中查询sku对象
   SkuInfo getSkuInfo(int skuId);


}
