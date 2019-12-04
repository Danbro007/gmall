package com.danbro.gmall.payment.service;

import com.danbro.gmall.service.utils.util.ActiveMqUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@SpringBootTest
class GmallPaymentServiceApplicationTests {

    @Autowired
    ActiveMqUtil activeMqUtil;

    @Test
     void test02() throws JMSException {
        ConnectionFactory connectionFactory = activeMqUtil.getConnectionFactory();
        Connection connection = connectionFactory.createConnection();
        System.out.println(connection);

    }

}
