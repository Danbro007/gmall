package com.danbro.gmall.cart.service;

import com.danbro.gmall.api.dto.OmsCartItemDto;
import com.danbro.gmall.api.service.CartService;
import com.danbro.gmall.api.service.OrderService;
import com.danbro.gmall.cart.service.mapper.CartMapper;
import com.danbro.gmall.service.utils.util.MqProducerUtil;
import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.JMSException;
import java.util.TreeMap;

@SpringBootTest
class GmallCartServiceApplicationTests {

    @Autowired
    CartService cartService;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    MqProducerUtil mqProducerUtil;



    @Test
    void contextLoads() {
        OmsCartItemDto itemBySkuId = cartService.getItemBySkuId(105L, "1");
        if (itemBySkuId == null) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }

    @Test
    void test01() {
        System.out.println("2222");
    }

    @Test
    void test02() throws JMSException {
        ActiveMQMapMessage activeMQMapMessage = new ActiveMQMapMessage();
        activeMQMapMessage.setString("name","test");
        ActiveMQQueue queue = new ActiveMQQueue("test");
        jmsMessagingTemplate.convertAndSend(queue,activeMQMapMessage);
    }
    @Test
    void test03() throws JMSException {
        ActiveMQMapMessage activeMQMapMessage = new ActiveMQMapMessage();
        activeMQMapMessage.setString("name","test");
        mqProducerUtil.setMessage("test02",activeMQMapMessage);
    }

}
