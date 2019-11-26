package com.danbro.gmall.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Danrbo
 * @date 2019/9/10 11:01
 * description
 **/
@Data
public class PmsSkuInfoPo implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long productId;

    private BigDecimal price;

    private String skuName;

    private Double weight;

    private String skuDesc;

    private Long catalog3Id;

    private String skuDefaultImg;

}
