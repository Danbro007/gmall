package com.danbro.gmall.manage.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.danbro.gmall.api.dto.PmsSkuInfoDto;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/17 12:57
 * description
 **/
public interface PmsSkuInfoMapper extends BaseMapper<PmsSkuInfoDto> {

    /**
     * 通过spuId查找到所有的相关的sku
     *
     * @param spuId spuId
     * @return 相关的sku
     */
    List<PmsSkuInfoDto> selectSkuSaleAttrListCheckBySpu(Long spuId);

}
