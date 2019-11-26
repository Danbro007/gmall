package com.danbro.gmall.api.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.danbro.gmall.api.po.PmsProductSaleAttrPo;
import lombok.Data;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/18 13:52
 * description
 **/
@Data
@TableName(value = "pms_product_sale_attr")
public class PmsProductSaleAttrDto extends PmsProductSaleAttrPo {

    @TableField(exist = false)
    private List<PmsProductSaleAttrValueDto> saleAttrValueList;
}
