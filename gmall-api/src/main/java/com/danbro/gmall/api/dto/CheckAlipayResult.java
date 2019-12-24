package com.danbro.gmall.api.dto;

import lombok.Data;

/**
 * @Classname CheckAlipayResult
 * @Description TODO
 * @Date 2019/12/9 14:30
 * @Author Danrbo
 */
@Data
public class CheckAlipayResult {

    private String tradeStatus;

    private String outTradeNo;

    private String tradeNo;

}
