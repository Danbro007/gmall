package com.danbro.gmall.api.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Danrbo
 * @date 2019/9/17 12:46
 * description
 **/

@Data
@TableName(value = "pms_product_sale_attr_value")
public class PmsProductSaleAttrValue implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long productId;

    private Long saleAttrId;

    private String saleAttrValueName;

    @TableField(exist = false)
    private String isChecked;

}
