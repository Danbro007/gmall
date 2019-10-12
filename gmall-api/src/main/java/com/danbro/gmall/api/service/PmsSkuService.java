package com.danbro.gmall.api.service;

import com.danbro.gmall.api.bean.InsertSkuInfo;
import com.danbro.gmall.api.bean.PmsSkuInfo;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/17 18:54
 * description
 **/
public interface PmsSkuService {


    /**
     * 添加sku信息
     * @param insertSkuInfo sku信息
     * @return 成功信息
     */
    int addSkuInfo(InsertSkuInfo insertSkuInfo);

    /**
     * 通过skuId访问DB获取单个sku信息
     * @param skuId skuId
     * @return sku的信息
     */
    PmsSkuInfo getSkuFromDb(Long skuId);

    /**
     * 通过spuId查找到所有的相关的sku
     *
     * @param spuId spuId
     * @return 相关的sku
     */
    String selectSkuSaleAttrListCheckBySpu(Long spuId);

    /**
     * 通过skuId从redis找打sku,如果没有则访问db
     * @param skuId skuId
     * @return sku信息
     */
    PmsSkuInfo getSkuById(Long skuId);


    /**
     * 返回所有sku列表
     * @param catalog3Id 三级目录id
     * @return sku列表
     */
    List<PmsSkuInfo> getAllSku(Long catalog3Id);

}
