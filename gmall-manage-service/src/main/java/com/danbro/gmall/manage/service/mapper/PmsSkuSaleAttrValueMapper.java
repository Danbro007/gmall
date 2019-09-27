package com.danbro.gmall.manage.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.danbro.gmall.api.bean.PmsProductSaleAttr;
import com.danbro.gmall.api.bean.PmsSkuSaleAttrValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/17 12:57
 * description
 **/
public interface PmsSkuSaleAttrValueMapper  extends BaseMapper<PmsSkuSaleAttrValue> {

    List<PmsSkuSaleAttrValue> selectSpuSaleAttrListCheckBySku(@Param("product_id") Long productId, @Param("sku_id") Long skuId);

}
