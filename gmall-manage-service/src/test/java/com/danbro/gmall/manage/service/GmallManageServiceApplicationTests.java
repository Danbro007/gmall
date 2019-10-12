package com.danbro.gmall.manage.service;

import com.danbro.gmall.api.bean.PmsBaseAttrInfo;
import com.danbro.gmall.api.bean.PmsProductSaleAttr;
import com.danbro.gmall.api.bean.PmsSkuInfo;
import com.danbro.gmall.manage.service.mapper.PmsBaseAttrInfoMapper;
import com.danbro.gmall.manage.service.mapper.PmsProductSaleAttrMapper;
import com.danbro.gmall.manage.service.mapper.PmsSkuInfoMapper;
import com.danbro.gmall.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
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

    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
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

    @Test
    public void test04(){
        List<PmsBaseAttrInfo> attrValueByValueId = pmsBaseAttrInfoMapper.getAttrValueByValueId("41,45,46");
        for (PmsBaseAttrInfo pmsBaseAttrInfo : attrValueByValueId) {
            System.out.println(pmsBaseAttrInfo);
        }
    }

    @Test
    public void test05(){
       String[] a = {"1","2","3"};
        HashSet<Long> b = new HashSet<>();
        b.add(1L);
        b.add(2L);
        Iterator<Long> iterator = b.iterator();
        while (iterator.hasNext()){
            Long next = iterator.next();
            if (Arrays.asList(a).contains(next.toString())){
                iterator.remove();
            }
        }
        for (Long aLong : b) {
            System.out.println(aLong);
        }
    }
    @Test
    public void test06(){

    }

}
