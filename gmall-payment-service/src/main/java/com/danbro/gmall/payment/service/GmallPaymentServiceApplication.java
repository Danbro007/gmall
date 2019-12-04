package com.danbro.gmall.payment.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

@SpringBootApplication
@MapperScan(basePackages = "com.danbro.gmall.payment.service.mapper")
@ComponentScan(basePackages = "com.danbro.gmall")
public class GmallPaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallPaymentServiceApplication.class, args);
    }

}
