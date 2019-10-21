package com.danbro.gmall.manage.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.danbro.gmall.api.dto.PmsSkuSaleAttrValueDto;
import com.danbro.gmall.api.po.PmsSkuSaleAttrValuePo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/17 12:57
 * description
 **/
public interface PmsSkuSaleAttrValueMapper  extends BaseMapper<PmsSkuSaleAttrValueDto> {

    List<PmsSkuSaleAttrValueDto> selectSkuSaleAttrListCheckBySpu(@Param("product_id") Long productId, @Param("sku_id") Long skuId);

}
