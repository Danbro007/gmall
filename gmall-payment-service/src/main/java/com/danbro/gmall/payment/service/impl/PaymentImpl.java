package com.danbro.gmall.payment.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.danbro.gmall.api.dto.CheckAlipayResult;
import com.danbro.gmall.api.po.PaymentInfoPo;
import com.danbro.gmall.api.service.PaymentService;
import com.danbro.gmall.payment.service.mapper.PaymentMapper;
import com.danbro.gmall.service.utils.util.MqProducerUtil;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.jms.JMSException;
import java.util.HashMap;


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

    @Autowired
    AlipayClient alipayClient;

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public void insert(PaymentInfoPo paymentInfo) {
        int insert = paymentMapper.insert(paymentInfo);
        if (insert == 1) {
            ActiveMQMapMessage mapMessage = new ActiveMQMapMessage();
            try {
                mapMessage.setString("trade_no_code", paymentInfo.getOrderSn());
            } catch (JMSException e) {
                e.printStackTrace();
            }
            mqProducerUtil.setMessage("PAYMENT_RESULT_QUEUE", mapMessage);
        }
    }

    @Override
    public void checkPaymentSuccessQueue(String orderSn,int count) {
        ActiveMQMapMessage mapMessage = new ActiveMQMapMessage();
        try {
            mapMessage.setString("trade_no_code", orderSn);
            //延迟30秒发送到支付检查队列里
            mapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 1000 * 10);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        mqProducerUtil.setMessage("PAYMENT_CHECK_QUEUE", mapMessage);
        redisTemplate.opsForValue().set("paymentCount:" + orderSn, count);
    }

    @Override
    public CheckAlipayResult checkAlipayPayment(String tradeNoCode) {

        CheckAlipayResult checkAlipayResult = new CheckAlipayResult();

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        HashMap<String, String> paramMap = new HashMap<>(16);
        paramMap.put("out_trade_no", tradeNoCode);
        request.setBizContent(JSON.toJSONString(paramMap));
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response != null && response.isSuccess()) {
            System.out.println("调用成功");
            BeanUtils.copyProperties(response, checkAlipayResult);
        }
        return checkAlipayResult;
}

    @Override
    public void updatePayment(PaymentInfoPo paymentInfo) {
        QueryWrapper<PaymentInfoPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_sn",paymentInfo.getOrderSn());
        PaymentInfoPo paymentInfoFromDb = paymentMapper.selectOne(queryWrapper);
        //幂等性检查
        if (paymentInfoFromDb.getPaymentStatus() != null && paymentInfoFromDb.getPaymentStatus().equals(true)){
            return;
        }
        ActiveMQMapMessage mapMessage = new ActiveMQMapMessage();
        try {
            mapMessage.setString("orderSn",paymentInfo.getOrderSn());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        //添加到订单成功队列
        mqProducerUtil.setMessage("PAYMENT_SUCCESS_QUEUE",mapMessage);
    }

    @Override
    public PaymentInfoPo selectPaymentByTradeNoCode(String outTradeNo) {
        QueryWrapper<PaymentInfoPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_sn", outTradeNo);
        return paymentMapper.selectOne(queryWrapper);
    }

}
