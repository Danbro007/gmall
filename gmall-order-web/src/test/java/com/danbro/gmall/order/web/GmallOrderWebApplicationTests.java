package com.danbro.gmall.order.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class GmallOrderWebApplicationTests {

    @Test
    void contextLoads() {

        String password = "123456";

        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(passwordMd5);
    }

}
