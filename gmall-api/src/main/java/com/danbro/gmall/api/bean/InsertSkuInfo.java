package com.danbro.gmall.api.bean;

import lombok.Data;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/11 20:43
 * description
 **/
public class InsertSkuInfo extends PmsSkuInfo{
    private List<PmsSkuSaleAttrValue> skuSaleAttrValueList;

    public List<PmsSkuSaleAttrValue> getSkuSaleAttrValueList() {
        return skuSaleAttrValueList;
    }

    public void setSkuSaleAttrValueList(List<PmsSkuSaleAttrValue> skuSaleAttrValueList) {
        this.skuSaleAttrValueList = skuSaleAttrValueList;
    }
}
