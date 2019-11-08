//package com.danbro.gmall.conf;
//
//import com.danbro.gmall.util.RedisUtil;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author Danrbo
// * @date 2019/9/23 13:32
// * description
// **/
//@Configuration
//public class RedisConf {
//    @Value("${spring.redis.host:disabled}")
//    private String host;
//
//    @Value("${spring.redis.port:6379}")
//    private int port;
//
//    @Value("${spring.redis.database:0}")
//    private int database;
//
//
//    @Bean
//    public RedisUtil getRedisUtil() {
//        if (host.equals("disabled")) {
//            return null;
//        }
//        RedisUtil redisUtil = new RedisUtil();
//        redisUtil.initPool(host, port, database);
//        return redisUtil;
//    }
//
//
//}
