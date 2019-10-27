package com.jd.essearchservice.service.impl;

import com.jd.essearchapi.entity.SkuInfo;
import com.jd.essearchinterface.service.SkuInfoService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Service
public class SkuInfoServiceImpl implements SkuInfoService{

    @Autowired
    JestClient jestClient;

    /**
     * 上传mysql数据到es中
     * @param skuInfo
     */
    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        Index index = new Index.Builder(skuInfo).index("jd").type("SkuInfo").id(skuInfo.getId() + "").build();
        try {
            System.out.println("----index--->"+index);
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
