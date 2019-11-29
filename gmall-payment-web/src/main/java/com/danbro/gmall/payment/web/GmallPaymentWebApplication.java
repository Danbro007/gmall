package com.danbro.gmall.payment.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.danbro.gmall")
public class GmallPaymentWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallPaymentWebApplication.class, args);
    }

}
