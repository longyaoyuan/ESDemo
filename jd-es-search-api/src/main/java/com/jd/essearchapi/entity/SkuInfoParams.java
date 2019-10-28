package com.jd.essearchapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class SkuInfoParams implements Serializable {
    private String keyword;
    private int catalog3Id;
    private int pageNo;//当前页码
    private int pageSize; //当前页显示的条数

}
