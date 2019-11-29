package com.danbro.gmall.payment.web.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:alipay.properties")
public class AlipayConfig {

    @Value("${alipay_url}")
    private String alipayUrl;
    @Value("${app_private_key}")
    private String appPrivateKey;
    @Value("${app_id}")
    private String appId;

    private final static String FORMAT = "json";
    private final static String CHARSET = "utf-8";
    private final static String SIGN_TYPE = "RSA2";

    public static String returnPaymentUrl;
    public static String notifyPaymentUrl;
    public static String returnOrderUrl;
    public static String alipayPublicKey;

    @Value("${alipay_public_key}")
    public void setAlipayPublicKey(String alipayPublicKey) {
        AlipayConfig.alipayPublicKey = alipayPublicKey;
    }

    @Value("${return_payment_url}")
    public void setReturnUrl(String returnPaymentUrl) {
        AlipayConfig.returnPaymentUrl = returnPaymentUrl;
    }

    @Value("${notify_payment_url}")
    public void setNotifyUrl(String notifyPaymentUrl) {
        AlipayConfig.notifyPaymentUrl = notifyPaymentUrl;
    }

    @Value("${return_order_url}")
    public void setReturnOrderUrl(String returnOrderUrl) {
        AlipayConfig.returnOrderUrl = returnOrderUrl;
    }

    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(alipayUrl, appId, appPrivateKey, FORMAT, CHARSET, alipayPublicKey, SIGN_TYPE);
    }
}