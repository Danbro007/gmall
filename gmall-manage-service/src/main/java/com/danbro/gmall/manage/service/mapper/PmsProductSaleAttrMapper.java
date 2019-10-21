package com.danbro.gmall.manage.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.danbro.gmall.api.dto.PmsProductSaleAttrDto;
import com.danbro.gmall.api.po.PmsProductSaleAttrPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/17 12:53
 * description
 **/
public interface PmsProductSaleAttrMapper  extends BaseMapper<PmsProductSaleAttrDto> {

    List<PmsProductSaleAttrDto> selectSpuSaleAttrListCheckBySku(@Param("product_id") Long productId, @Param("sku_id") Long skuId);
}
