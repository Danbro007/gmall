package com.danbro.gmall.cart.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.danbro.gmall.cart.service.mapper")
@ComponentScan(basePackages = "com.danbro.gmall")
public class GmallCartServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallCartServiceApplication.class, args);
    }

}
