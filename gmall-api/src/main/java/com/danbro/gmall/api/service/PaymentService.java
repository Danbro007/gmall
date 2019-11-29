package com.danbro.gmall.api.service;

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
     * @param paymentInfo 支付信息
     */
    void insert(PaymentInfoPo paymentInfo);

}
