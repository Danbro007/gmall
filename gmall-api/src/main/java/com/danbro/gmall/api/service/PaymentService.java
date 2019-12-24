package com.danbro.gmall.api.service;

import com.danbro.gmall.api.dto.CheckAlipayResult;
import com.danbro.gmall.api.po.PaymentInfoPo;

/**
 * @Classname PaymentService
 * @Description TODO 支付接口
 * @Date 2019/11/29 14:40
 * @Author Danrbo
 */
public interface PaymentService {

    /**
     * 添加支付记录
     *
     * @param paymentInfo 支付信息
     */
    void insert(PaymentInfoPo paymentInfo);

    /**
     * 把外部订单号放到判断交易是否成功的队列里
     * @param orderSn 外部订单号
     * @param count 计数器
     */
    void checkPaymentSuccessQueue(String orderSn,int count);

    /**
     * 调用支付宝的api通过订单号查询支付状态
     * @param tradeNoCode 外部订单号
     * @return 支付状态信息
     */
    CheckAlipayResult checkAlipayPayment(String tradeNoCode);

    /**
     * 更新支付状态
     * @param paymentInfo 支付信息
     */
    void updatePayment(PaymentInfoPo paymentInfo);

    /**
     * 通过外部订单号查找到支付信息
     * @param outTradeNo 外部订单号
     * @return 支付信息
     */
    PaymentInfoPo selectPaymentByTradeNoCode(String outTradeNo);
}
