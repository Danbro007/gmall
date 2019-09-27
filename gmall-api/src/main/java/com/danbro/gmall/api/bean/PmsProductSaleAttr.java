package com.danbro.gmall.api.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/17 12:44
 * description
 **/
@Data
@TableName(value = "pms_product_sale_attr")
public class PmsProductSaleAttr implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long productId;

    private Long saleAttrId;

    private String saleAttrName;

    @TableField(exist = false)
    private List<PmsProductSaleAttrValue> saleAttrValueList;
}