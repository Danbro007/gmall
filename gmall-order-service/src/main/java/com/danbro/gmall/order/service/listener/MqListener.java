package com.danbro.gmall.order.service.listener;

import com.danbro.gmall.api.dto.OmsOrderDto;
import com.danbro.gmall.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import java.util.Date;

/**
 * @Classname MqListener
 * @Description TODO
 * @Date 2019/12/3 14:18
 * @Author Danrbo
 */


@Component
public class MqListener {

    @Autowired
    OrderService orderService;

    @JmsListener(destination = "PAYMENT_RESULT_QUEUE", containerFactory = "jmsQueueListener")
    public void updateOrderStatus(MapMessage mapMessage) {
        try {
            String tradeNoCode = mapMessage.getString("trade_no_code");
            OmsOrderDto omsOrderDto = new OmsOrderDto();
            omsOrderDto.setOrderSn(tradeNoCode);
            omsOrderDto.setStatus(1);
            omsOrderDto.setPaymentTime(new Date());
            orderService.updateOrderStatusByOrderSn(omsOrderDto);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
