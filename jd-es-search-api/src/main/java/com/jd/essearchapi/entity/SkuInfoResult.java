package com.jd.essearchapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SkuInfoResult implements Serializable {
    //skuInfo结果集
    private List<SkuInfo> skuInfoList;
    //总条数
    private long total;
    //总页数
    private long totalPages;

}
