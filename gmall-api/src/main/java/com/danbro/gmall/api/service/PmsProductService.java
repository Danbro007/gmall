package com.danbro.gmall.api.service;

import com.danbro.gmall.api.bean.PmsProductInfo;
import com.danbro.gmall.api.bean.PmsProductSaleAttr;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/11 13:52
 * description
 **/
public interface PmsProductService {
    /**
     * 通过目录id查找到相关的spu
     * @param id 目录id
     * @return 相关的spu
     */
    List<PmsProductInfo> getProductInfoListByCatalogId(Long id);

    /**
     * 添加spu信息
     * @param pmsProductInfo spu信息
     * @return success信息
     */
    int addProductInfo(PmsProductInfo pmsProductInfo);

    /**
     * 通过spuId找到所有相关的sku,通过skuId找到选中的sku
     * @param productId spuId
     * @param skuId skuId
     * @return 相关sku
     */
    List<PmsProductSaleAttr> selectSpuSaleAttrListCheckBySku(Long productId,Long skuId);


}