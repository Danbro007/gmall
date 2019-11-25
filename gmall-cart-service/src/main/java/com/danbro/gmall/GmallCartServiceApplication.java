package com.danbro.gmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.danbro.gmall.cart.service.mapper")
public class GmallCartServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallCartServiceApplication.class, args);
    }

}
