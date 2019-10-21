package com.danbro.gmall.api.service;

import com.danbro.gmall.api.dto.PmsProductInfoDto;
import com.danbro.gmall.api.dto.PmsProductSaleAttrDto;
import com.danbro.gmall.api.po.PmsProductInfoPo;
import com.danbro.gmall.api.po.PmsProductSaleAttrPo;
import com.danbro.gmall.api.vo.PmsProductInfoVo;

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
    List<PmsProductInfoDto> getProductInfoListByCatalogId(Long id);

    /**
     * 添加spu信息
     * @param pmsProductInfoVo spu信息Dto
     * @return success信息
     */
    int addProductInfo(PmsProductInfoVo pmsProductInfoVo);

    /**
     * 通过spuId找到所有相关的sku,通过skuId找到选中的sku
     * @param productId spuId
     * @param skuId skuId
     * @return 相关sku
     */
    List<PmsProductSaleAttrDto> selectSpuSaleAttrListCheckBySku(Long productId, Long skuId);


}
