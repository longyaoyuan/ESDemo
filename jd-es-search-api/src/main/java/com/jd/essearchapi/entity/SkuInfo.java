package com.jd.essearchapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SkuInfo implements Serializable {


    private int id;


    private int spuId;


    private double price;


    private String skuName;


    private String skuDesc;

    private double weight;


    private int tmId;


    private int catalog3Id;


    private String skuDefaultImg;

}
