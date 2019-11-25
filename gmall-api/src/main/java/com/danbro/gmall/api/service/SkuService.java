package com.danbro.gmall.api.service;

import com.danbro.gmall.api.dto.PmsSkuInfoDto;
import com.danbro.gmall.api.vo.PmsSkuInfoVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/17 18:54
 * description
 **/
public interface SkuService {


    /**
     * 添加sku信息
     * @param pmsSkuInfoVo 要添加的sku信息
     * @return 成功信息
     */
    int addSkuInfo(PmsSkuInfoVo pmsSkuInfoVo);

    /**
     * 通过skuId访问DB获取单个sku信息
     * @param skuId skuId
     * @return sku的信息
     */
    PmsSkuInfoDto getSkuFromDb(Long skuId);

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
    PmsSkuInfoDto getSkuById(Long skuId);


    /**
     * 返回所有sku列表
     * @param catalog3Id 三级目录id
     * @return sku列表
     */
    List<PmsSkuInfoDto> getAllSku(Long catalog3Id);

    /**
     * 通过商品id 价格判定检验价格
     * @param skuId 商品id
     * @param price 商品价格
     * @return true：校验成功 false:校验失败
     */
    Boolean checkSkuPrice(Long skuId, BigDecimal price);

}
