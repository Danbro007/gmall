package com.danbro.gmall.cart.service;

import com.danbro.gmall.api.dto.OmsCartItemDto;
import com.danbro.gmall.api.service.CartService;
import com.danbro.gmall.cart.service.mapper.CartMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.TreeMap;

@SpringBootTest
class GmallCartServiceApplicationTests {

    @Autowired
    CartService cartService;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
        OmsCartItemDto itemBySkuId = cartService.getItemBySkuId(105L, "1");
        if (itemBySkuId == null) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }

    @Test
    void test01() {
        System.out.println("2222");
    }

    @Test
    void test02() {
        TreeMap<Long, OmsCartItemDto> longOmsCartItemDtoTreeMap = new TreeMap<>();
        OmsCartItemDto omsCartItemDto1 = new OmsCartItemDto();
        omsCartItemDto1.setId(3L);
        OmsCartItemDto omsCartItemDto = new OmsCartItemDto();
        omsCartItemDto.setId(1L);
        OmsCartItemDto omsCartItemDto2 = new OmsCartItemDto();
        omsCartItemDto2.setId(4L);
        OmsCartItemDto omsCartItemDto3 = new OmsCartItemDto();
        omsCartItemDto3.setId(2L);
        longOmsCartItemDtoTreeMap.put(omsCartItemDto1.getId(),omsCartItemDto1);
        longOmsCartItemDtoTreeMap.put(omsCartItemDto.getId(),omsCartItemDto);
        longOmsCartItemDtoTreeMap.put(omsCartItemDto2.getId(),omsCartItemDto2);
        longOmsCartItemDtoTreeMap.put(omsCartItemDto3.getId(),omsCartItemDto3);
        System.out.println(1);
    }
}
