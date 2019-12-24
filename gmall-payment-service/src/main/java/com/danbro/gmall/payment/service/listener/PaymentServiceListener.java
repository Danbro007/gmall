package com.danbro.gmall.payment.service.listener;

import com.danbro.gmall.api.dto.CheckAlipayResult;
import com.danbro.gmall.api.po.PaymentInfoPo;
import com.danbro.gmall.api.service.PaymentService;
import com.danbro.gmall.service.utils.util.MqProducerUtil;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * @Classname PaymentSuccessListener
 * @Description TODO
 * @Date 2019/12/9 15:10
 * @Author Danrbo
 */
@SuppressWarnings("unchecked")
@Component
public class PaymentServiceListener {
    private static final String SUCCESS_FLAG = "TRADE_SUCCESS";
    @Autowired
    PaymentService paymentService;

    @Autowired
    RedisTemplate redisTemplate;

    @JmsListener(destination = "PAYMENT_CHECK_QUEUE", containerFactory = "jmsQueueListener")
    public void checkPayment(MapMessage mapMessage) {
        try {
            String tradeNoCode = mapMessage.getString("trade_no_code");
            PaymentInfoPo paymentInfoPo = paymentService.selectPaymentByTradeNoCode(tradeNoCode);
            int count = (int) redisTemplate.opsForValue().get("paymentCount:" + tradeNoCode);
            redisTemplate.delete("paymentCount:" + tradeNoCode);
            CheckAlipayResult checkAlipayResult = paymentService.checkAlipayPayment(tradeNoCode);
            if (checkAlipayResult.getTradeStatus() != null && checkAlipayResult.getTradeStatus().equals(SUCCESS_FLAG)) {
                paymentInfoPo.setPaymentStatus(true);
                paymentService.updatePayment(paymentInfoPo);
                System.out.println("提交到支付成功队列");
            } else {
                if (count > 0) {
                    count = count - 1;
                    System.out.println("交易失败,还有" + count + "次机会");
                    paymentService.checkPaymentSuccessQueue(tradeNoCode, count);
                } else {
                    System.out.println("交易关闭");
                }
            }
        } catch (
                JMSException e) {
            e.printStackTrace();
        }
    }

    @JmsListener(destination = "PAYMENT_SUCCESS_QUEUE", containerFactory = "jmsQueueListener")
    public void successPayment(MapMessage mapMessage) {
        try {
            String orderSn = mapMessage.getString("orderSn");
            PaymentInfoPo paymentInfoPoFromDb = paymentService.selectPaymentByTradeNoCode(orderSn);
            paymentInfoPoFromDb.setPaymentStatus(true);
            System.out.println("支付成功");

        } catch (JMSException e) {
            e.printStackTrace();
        }


    }
}
