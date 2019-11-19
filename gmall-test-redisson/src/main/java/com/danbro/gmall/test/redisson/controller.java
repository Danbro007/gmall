package com.danbro.gmall.test.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Danrbo
 * @date 2019/11/18 21:50
 * description
 **/
@Controller
@ResponseBody
public class controller {


    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/test")
    public String test(){
        RLock lock = redissonClient.getLock("lock");
        lock.lock();
        try {
             Integer num = (Integer)redisTemplate.opsForValue().get("num");
             if (num == null){
                 num = 1;
             }
             redisTemplate.opsForValue().set("num",num+1);
            //从缓存获取 没有的话到数据库获取
            System.out.println("----->" + num);

        } finally {
            lock.unlock();
        }
        return "success";
    }

}
