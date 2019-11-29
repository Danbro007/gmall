package com.danbro.gmall.payment.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.po.PaymentInfoPo;
import com.danbro.gmall.api.service.PaymentService;
import com.danbro.gmall.payment.service.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Classname PaymentImpl
 * @Description TODO
 * @Date 2019/11/29 14:45
 * @Author Danrbo
 */
@Service
public class PaymentImpl implements PaymentService {

    @Autowired
    PaymentMapper paymentMapper;

    @Override
    public void insert(PaymentInfoPo paymentInfo) {
        paymentMapper.insert(paymentInfo);
    }
}
