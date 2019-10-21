package com.danbro.gmall.manage.service;

import com.danbro.gmall.api.dto.PmsBaseAttrInfoDto;
import com.danbro.gmall.api.dto.PmsProductSaleAttrDto;
import com.danbro.gmall.api.dto.PmsSkuInfoDto;
import com.danbro.gmall.api.po.PmsBaseAttrInfoPo;
import com.danbro.gmall.api.po.PmsProductSaleAttrPo;
import com.danbro.gmall.api.po.PmsSkuInfoPo;
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

        List<PmsProductSaleAttrDto> pmsProductSaleAttrDtoList = pmsProductSaleAttrMapper.selectSpuSaleAttrListCheckBySku(65L, 105L);
        for (PmsProductSaleAttrDto pmsProductSaleAttrDto : pmsProductSaleAttrDtoList) {
            System.out.println(pmsProductSaleAttrDto);
        }

    }

    @Test
    public void test2(){
        List<PmsSkuInfoDto> pmsSkuInfoDtoList = pmsSkuInfoMapper.selectSkuSaleAttrListCheckBySpu(66L);
        for (PmsSkuInfoDto pmsSkuInfoDto : pmsSkuInfoDtoList) {
            System.out.println(pmsSkuInfoDto);
        }
    }

    @Test
    public void test03(){
        Jedis jedis = redisUtil.getJedis();
        System.out.println(jedis);
    }

    @Test
    public void test04(){
        List<PmsBaseAttrInfoDto> attrValueByValueId = pmsBaseAttrInfoMapper.getAttrValueByValueId("41,45,46");
        for (PmsBaseAttrInfoDto pmsBaseAttrInfoDto : attrValueByValueId) {
            System.out.println(pmsBaseAttrInfoDto);
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
