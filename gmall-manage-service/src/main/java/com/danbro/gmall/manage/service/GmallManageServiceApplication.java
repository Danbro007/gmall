package com.danbro.gmall.manage.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.danbro.gmall.manage.service.mapper")
@ComponentScan(basePackages = "com.danbro.gmall")
public class GmallManageServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(GmallManageServiceApplication.class, args);
    }

}
