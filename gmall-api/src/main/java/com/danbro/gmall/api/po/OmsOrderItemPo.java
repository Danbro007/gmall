package com.danbro.gmall.api.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class OmsOrderItemPo implements Serializable {

    private Long id;
    private Long orderId;
    private String orderSn;
    private Long productId;
    private String productPic;
    private String productName;
    private String productBrand;
    private String productSn;
    private String productPrice;
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
