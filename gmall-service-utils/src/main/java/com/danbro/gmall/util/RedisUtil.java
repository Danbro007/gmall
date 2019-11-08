//package com.danbro.gmall.util;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * @author Danrbo
// * @date 2019/9/23 13:27
// * description
// **/
//public class RedisUtil {
//
//    private JedisPool jedisPool;
//
//    public void initPool(String host, int port, int database) {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(3000);
//        jedisPoolConfig.setBlockWhenExhausted(true);
//        jedisPoolConfig.setMaxWaitMillis(10 * 1000);
//        jedisPoolConfig.setTestOnBorrow(true);
//        jedisPoolConfig.setMaxTotal(2000);
//        jedisPool = new JedisPool(jedisPoolConfig, host, port, database);
//    }
//
//    public Jedis getJedis() {
//        return jedisPool.getResource();
//    }
//}
