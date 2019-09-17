package com.danbro.gmall.api.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
/**
 * @author Danrbo
 * @date 2019/9/10 11:01
 * description
 **/
@Data
@TableName(value = "pms_sku_attr_value")
public class PmsSkuAttrValue implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long attrId;

    private String valueId;

    private Long skuId;

}
