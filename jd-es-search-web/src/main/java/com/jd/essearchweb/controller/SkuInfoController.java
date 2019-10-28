package com.jd.essearchweb.controller;

import com.alibaba.fastjson.JSON;
import com.jd.essearchapi.entity.SkuInfo;
import com.jd.essearchapi.entity.SkuInfoParams;
import com.jd.essearchapi.entity.SkuInfoResult;
import com.jd.essearchinterface.service.ManagerService;
import com.jd.essearchinterface.service.SkuInfoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SkuInfoController {

    @Reference
    SkuInfoService skuInfoService;

    @Reference
    ManagerService managerService;


    @ResponseBody
    @RequestMapping("/saveSkuInfo")
    public String saveSkuInfo(@RequestParam("skuId") int skuId) {
        System.out.println("-----controller------>" + skuId);
        SkuInfo skuInfo = managerService.getSkuInfo(skuId);
        System.out.println("------>" + skuInfo.toString());

        skuInfoService.saveSkuInfo(skuInfo);

        return "ok";
    }

    /**
     * 按关键字查询
     * @param skuInfoParams
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchSkuInfo")
    public String searchSkuInfo(SkuInfoParams skuInfoParams) {
        SkuInfoResult skuInfoResult = skuInfoService.skuSearch(skuInfoParams);
        return JSON.toJSONString(skuInfoResult);
    }

}
