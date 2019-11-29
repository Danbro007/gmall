package com.danbro.gmall.api.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AlipayReturnParamVo {
    private String charset;
    /**
     * 商城的外部订单号
     */
    private String outTradeNo;
    /**
     * 时间戳
     */
    private String timestamp;
    private String sellerId;
    private String signType;
    private String appId;
    private String authAppId;
    /**
     * 支付宝那里的交易流水号
     */
    private String tradeNo;
    private String method;
    /**
     * 支付总价
     */
    private BigDecimal totalAmount;
    /**
     * 数字签名
     */
    private String sign;
    private String version;

}
