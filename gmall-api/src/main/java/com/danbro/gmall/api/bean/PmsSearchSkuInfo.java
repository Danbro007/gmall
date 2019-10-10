package com.danbro.gmall.api.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/8 11:05
 * description
 **/
@Data
public class PmsSearchSkuInfo implements Serializable {

    private Long id;
    private String skuName;
    private String skuDesc;
    private Long catalog3Id;
    private Long price;
    private String skuDefaultImg;
    private Long productId;
    private List<PmsSkuAttrValue> skuAttrValueList;

}
