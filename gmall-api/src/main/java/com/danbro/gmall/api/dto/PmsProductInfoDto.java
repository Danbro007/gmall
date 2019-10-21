package com.danbro.gmall.api.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.danbro.gmall.api.po.PmsProductInfoPo;
import lombok.Data;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/18 13:57
 * description
 **/
@Data
@TableName(value = "pms_product_info")
public class PmsProductInfoDto extends PmsProductInfoPo {
    @TableField(exist = false)
    private List<PmsProductSaleAttrDto> saleAttrList;
    @TableField(exist = false)
    private List<PmsProductImageDto> imageList;
}
