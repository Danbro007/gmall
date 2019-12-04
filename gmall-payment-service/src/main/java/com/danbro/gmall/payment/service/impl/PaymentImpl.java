package com.danbro.gmall.payment.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.po.PaymentInfoPo;
import com.danbro.gmall.api.service.PaymentService;
import com.danbro.gmall.payment.service.mapper.PaymentMapper;
import com.danbro.gmall.service.utils.util.MqProducerUtil;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;


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

    @Autowired
    MqProducerUtil mqProducerUtil;


    @Override
    public void insert(PaymentInfoPo paymentInfo) {
        int insert = paymentMapper.insert(paymentInfo);
        if (insert == 1){
            ActiveMQMapMessage mapMessage = new ActiveMQMapMessage();
            try {
                mapMessage.setString("trade_no_code",paymentInfo.getOrderSn());
            } catch (JMSException e) {
                e.printStackTrace();
            }
            mqProducerUtil.setMessage("PAYMENT_RESULT_QUEUE",mapMessage);
        }
    }

}
