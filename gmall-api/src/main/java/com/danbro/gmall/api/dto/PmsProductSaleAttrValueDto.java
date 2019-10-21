package com.danbro.gmall.api.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.danbro.gmall.api.po.PmsProductSaleAttrValuePo;
import lombok.Data;

/**
 * @author Danrbo
 * @date 2019/10/18 14:05
 * description
 **/
@Data
@TableName(value = "pms_product_sale_attr_value")
public class PmsProductSaleAttrValueDto extends PmsProductSaleAttrValuePo {
    @TableField(exist = false)
    private String isChecked;
}
