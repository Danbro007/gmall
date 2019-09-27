package com.danbro.gmall.manage.service;

import com.danbro.gmall.api.bean.PmsProductSaleAttr;
import com.danbro.gmall.api.bean.PmsSkuInfo;
import com.danbro.gmall.manage.service.mapper.PmsProductSaleAttrMapper;
import com.danbro.gmall.manage.service.mapper.PmsSkuInfoMapper;
import com.danbro.gmall.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManageServiceApplicationTests {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;
    @Test
    public void contextLoads() {
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.selectSpuSaleAttrListCheckBySku(65L, 105L);
        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductSaleAttrs) {
            System.out.println(pmsProductSaleAttr);
        }

    }

    @Test
    public void test2(){
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuSaleAttrListCheckBySpu(66L);
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
            System.out.println(pmsSkuInfo);
        }
    }

    @Test
    public void test03(){
        Jedis jedis = redisUtil.getJedis();
        System.out.println(jedis);
    }

}
