package com.jd.essearchservice.service.impl;

import com.jd.essearchapi.entity.SkuInfo;
import com.jd.essearchinterface.service.ManagerService;
import com.jd.essearchservice.mapper.SkuInfoMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Service是dubbo的service
 */
@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    SkuInfoMapper skuInfoMapper;

    @Override
    public SkuInfo getSkuInfo(int skuId) {
        return skuInfoMapper.selectByPrimaryKey(skuId);
    }
}
