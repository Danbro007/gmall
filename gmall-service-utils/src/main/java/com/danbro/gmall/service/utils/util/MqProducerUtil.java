package com.danbro.gmall.service.utils.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @Classname MqProducerUtil
 * @Description TODO
 * @Date 2019/12/3 13:49
 * @Author Danrbo
 */
@Component
public class MqProducerUtil {
    @Autowired
    ActiveMqUtil activeMqUtil;

    public void setMessage(String queueName, MapMessage messageMap) {
        ConnectionFactory connectionFactory = activeMqUtil.getConnectionFactory();
        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            //开启事务
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            Queue queue = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(queue);
            producer.send(messageMap);
            //提交
            session.commit();
        } catch (JMSException e) {
            //发生异常 数据回滚
            try {
                if (session != null) {
                    session.rollback();
                }
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                //关闭连接和session
                if (connection != null) {
                    connection.close();
                }
                if (session != null) {
                    session.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
