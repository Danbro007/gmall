package com.danbro.gmall.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallUserServiceApplicationTests {

    @Test
    public void contextLoads() {
    }
    @Test
    public void test04(){
        String md5Password = DigestUtils.md5DigestAsHex("shan52902003".getBytes());
        System.out.println(md5Password);
    }
}
