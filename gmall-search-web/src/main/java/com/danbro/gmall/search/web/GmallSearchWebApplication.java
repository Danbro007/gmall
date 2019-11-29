package com.danbro.gmall.search.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.danbro.gmall")
public class GmallSearchWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallSearchWebApplication.class, args);
    }

}
