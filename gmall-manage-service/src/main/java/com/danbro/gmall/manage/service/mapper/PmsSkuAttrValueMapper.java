package com.danbro.gmall.manage.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.danbro.gmall.api.dto.PmsSkuAttrValueDto;
import com.danbro.gmall.api.po.PmsSkuAttrValuePo;

import java.util.HashMap;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/17 12:56
 * description
 **/
public interface PmsSkuAttrValueMapper extends BaseMapper<PmsSkuAttrValueDto> {
    List<PmsSkuAttrValueDto> selectAttrIdBySkuId(String skuIdSetStr);
}
