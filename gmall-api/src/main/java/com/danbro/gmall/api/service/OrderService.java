package com.danbro.gmall.api.service;

import com.danbro.gmall.api.dto.OmsOrderDto;

/**
 * @author Danrbo
 * @date 2019/11/22 14:03
 * description 订单服务
 **/

public interface OrderService {

    /**
     * 通过用户id生成交易码
     * @param memberId 用户id
     * @return 交易码
     */
    String getTradeCode(Long memberId);


    /**
     * 通过用户id来校验trandeCode
     * @param memberId 用户id
     * @param tradeCode 交易码
     * @return true：交易码校验通过 false;交易码校验失败
     */
    Boolean checkTradeCode(Long memberId, String tradeCode);

    /**
     * 保存订单
     * @param omsOrderDto 订单对象
     */
    void saveOrder(OmsOrderDto omsOrderDto);

}
