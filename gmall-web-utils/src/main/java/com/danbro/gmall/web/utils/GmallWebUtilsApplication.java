package com.danbro.gmall.web.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.danbro.gmall")
public class GmallWebUtilsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallWebUtilsApplication.class, args);
    }

}
