package com.danbro.gmall.order.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class GmallOrderServiceApplicationTests {

    @Test
    void contextLoads() {
        String password = "shan52902003";

        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(passwordMd5);



    }

}
