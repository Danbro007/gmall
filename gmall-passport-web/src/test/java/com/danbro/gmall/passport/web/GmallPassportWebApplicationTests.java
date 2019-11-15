package com.danbro.gmall.passport.web;

import com.danbro.gmall.common.utils.util.HttpClientUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GmallPassportWebApplicationTests {

    @Test
    void contextLoads() {
        String code = HttpClientUtil.doGet("https://api.weibo.com/oauth2/authorize?client_id=3216293170&response_type=code&redirect_uri=passport.gmall.com:8085/vlogin");
        System.out.println(code);
    }
}
