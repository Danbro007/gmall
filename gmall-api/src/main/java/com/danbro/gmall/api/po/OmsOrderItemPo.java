package com.danbro.gmall.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Danrbo
 * @date 2019/9/10 14:17
 * description
 **/
@Data
@TableName(value = "oms_order_item")
public class OmsOrderItemPo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private String orderSn;
    private Long productId;
    private String productPic;
    private String productName;
    private String productBrand;
    private String productSn;
    private BigDecimal productPrice;
    private Integer productQuantity;
    private Long productSkuId;
    private String productSkuCode;
    private Long productCategoryId;
    private String sp1;
    private String sp2;
    private String sp3;
    private String promotionName;
    private BigDecimal promotionAmount;
    private BigDecimal couponAmount;
    private BigDecimal integrationAmount;
    private String realAmount;
    private Integer giftIntegration;
    private Integer giftGrowth;
    private String productAttr;
}
