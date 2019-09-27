package com.danbro.gmall.redisson.test;

import com.danbro.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

/**
 * @author Danrbo
 * @date 2019/9/24 11:47
 * description
 **/

@RestController
public class Controller {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedissonClient redissonClient;

    @GetMapping("redisson")
    public String testRedisson() {
        Jedis jedis = redisUtil.getJedis();
        RLock lock = redissonClient.getLock("lock");
        lock.lock();
        try {
            String v = jedis.get("k");
            if (StringUtils.isBlank(v)) {
                v = "1";
            }
            System.out.println("---->" + v);
            jedis.set("k", Integer.parseInt(v) + 1 + "");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            jedis.close();
        }
        return "success";
    }

}


