package com.danbro.gmall.api.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.danbro.gmall.api.po.PmsSkuSaleAttrValuePo;
import lombok.Data;

/**
 * @author Danrbo
 * @date 2019/10/18 14:42
 * description
 **/
@Data
@TableName(value = "pms_sku_sale_attr_value")
public class PmsSkuSaleAttrValueDto extends PmsSkuSaleAttrValuePo {

    @TableField(exist = false)
    private String isChecked;

}
