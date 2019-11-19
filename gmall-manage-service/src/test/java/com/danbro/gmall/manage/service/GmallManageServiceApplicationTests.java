package com.danbro.gmall.manage.service;

import com.danbro.gmall.api.dto.PmsBaseAttrInfoDto;
import com.danbro.gmall.api.dto.PmsProductSaleAttrDto;
import com.danbro.gmall.api.dto.PmsSkuInfoDto;
import com.danbro.gmall.manage.service.mapper.PmsBaseAttrInfoMapper;
import com.danbro.gmall.manage.service.mapper.PmsProductSaleAttrMapper;
import com.danbro.gmall.manage.service.mapper.PmsSkuInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
public class GmallManageServiceApplicationTests {


    @Autowired
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;

    @Autowired
    RedisTemplate redisTemplate;

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
        Object o = redisTemplate.opsForValue().get("sku:105:info");
        System.out.println(o);
    }

}
