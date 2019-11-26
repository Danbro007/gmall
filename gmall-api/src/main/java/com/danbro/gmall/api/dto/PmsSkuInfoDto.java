package com.danbro.gmall.api.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.danbro.gmall.api.po.PmsSkuInfoPo;
import lombok.Data;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/18 14:40
 * description
 **/
@Data
@TableName(value = "pms_sku_info")
public class PmsSkuInfoDto extends PmsSkuInfoPo {

    @TableField(exist = false)
    List<PmsSkuImageDto> skuImageList;

    @TableField(exist = false)
    List<PmsSkuAttrValueDto> skuAttrValueList;

    @TableField(exist = false)
    private List<PmsSkuSaleAttrValueDto> skuSaleAttrValueList;



}
