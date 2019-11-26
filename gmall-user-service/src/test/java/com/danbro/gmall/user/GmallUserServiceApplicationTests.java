package com.danbro.gmall.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class GmallUserServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test04() {
        String md5Password = DigestUtils.md5DigestAsHex("shan52902003".getBytes());
        System.out.println(md5Password);
    }
}
