package com.danbro.gmall.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class PmsSkuSaleAttrValuePo implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long skuId;

    private Long saleAttrId;

    private Long saleAttrValueId;

    private String saleAttrName;

    private String saleAttrValueName;

}
