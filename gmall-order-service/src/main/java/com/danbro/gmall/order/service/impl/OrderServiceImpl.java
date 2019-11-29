package com.danbro.gmall.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.danbro.gmall.api.dto.OmsOrderDto;
import com.danbro.gmall.api.po.OmsOrderItemPo;
import com.danbro.gmall.api.service.CartService;
import com.danbro.gmall.api.service.OrderService;
import com.danbro.gmall.order.service.mapper.OrderMapper;
import com.danbro.gmall.order.service.mapper.OrderItemMapper;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Danrbo
 * @date 2019/11/22 14:26
 * description
 **/
@SuppressWarnings("unchecked")
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Reference
    CartService cartService;



    @Override
    public String getTradeCode(Long memberId) {
        String key = "User:" + memberId + ":tradeCode";
        redisTemplate.delete(key);
        String value = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(key, value, 60 * 3, TimeUnit.SECONDS);
        return value;
    }

    @Override
    public Boolean checkTradeCode(Long memberId,String tradeCode) {
        String lockName = "User" + memberId + ":orderLock";
        String key = "User:" + memberId + ":tradeCode";
        RLock lock = redissonClient.getLock(lockName);
        //分布式锁一分钟后失效
        lock.lock(60, TimeUnit.SECONDS);
        try {
            String tradeCodeFromCache = (String) redisTemplate.opsForValue().get(key);
            if (tradeCodeFromCache != null){
                //交易码校验成功 返回true
                if (tradeCodeFromCache.equals(tradeCode)){
                    redisTemplate.delete(key);
                    return true;
                }
            }
        }finally {
            //解开分布式锁
            lock.unlock();
        }
    return false;
    }

    @Override
    public void saveOrder(OmsOrderDto omsOrderDto) {
        if (omsOrderDto != null){
            orderMapper.insert(omsOrderDto);
            for (OmsOrderItemPo omsOrderItemPo : omsOrderDto.getOmsOrderItemPoList()) {
                omsOrderItemPo.setOrderId(omsOrderDto.getId());
                orderItemMapper.insert(omsOrderItemPo);
                cartService.deleteCartItem(Long.toString(omsOrderDto.getMemberId()),omsOrderItemPo.getProductSkuId());
            }
            cartService.syncCartCache(Long.toString(omsOrderDto.getMemberId()));
        }
    }

    @Override
    public OmsOrderDto selectOrderByOrderSn(String orderSn) {
        QueryWrapper<OmsOrderDto> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_sn",orderSn);
        return orderMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateOrder(OmsOrderDto omsOrderDto) {
        return orderMapper.updateById(omsOrderDto);
    }
}
