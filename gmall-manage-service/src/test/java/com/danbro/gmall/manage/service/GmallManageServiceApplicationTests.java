package com.danbro.gmall.manage.service;

import com.danbro.gmall.api.dto.PmsBaseAttrInfoDto;
import com.danbro.gmall.api.dto.PmsProductSaleAttrDto;
import com.danbro.gmall.api.dto.PmsSkuInfoDto;
import com.danbro.gmall.manage.service.mapper.PmsBaseAttrInfoMapper;
import com.danbro.gmall.manage.service.mapper.PmsProductSaleAttrMapper;
import com.danbro.gmall.manage.service.mapper.PmsSkuInfoMapper;
import com.danbro.gmall.service.utils.util.ActiveMqUtil;
import com.danbro.gmall.service.utils.util.MqProducerUtil;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.jms.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
class GmallManageServiceApplicationTests {

    @Autowired
    MqProducerUtil mqProducerUtil;
    @Autowired
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void contextLoads() {

        List<PmsProductSaleAttrDto> pmsProductSaleAttrDtoList = pmsProductSaleAttrMapper.selectSpuSaleAttrListCheckBySku(65L, 105L);
        for (PmsProductSaleAttrDto pmsProductSaleAttrDto : pmsProductSaleAttrDtoList) {
            System.out.println(pmsProductSaleAttrDto);
        }

    }

    @Test
    void test2() {
        List<PmsSkuInfoDto> pmsSkuInfoDtoList = pmsSkuInfoMapper.selectSkuSaleAttrListCheckBySpu(66L);
        for (PmsSkuInfoDto pmsSkuInfoDto : pmsSkuInfoDtoList) {
            System.out.println(pmsSkuInfoDto);
        }
    }

    @Test
    void test03() {
        System.out.println("2222");
    }

    @Test
    void test04() {
        List<PmsBaseAttrInfoDto> attrValueByValueId = pmsBaseAttrInfoMapper.getAttrValueByValueId("41,45,46");
        for (PmsBaseAttrInfoDto pmsBaseAttrInfoDto : attrValueByValueId) {
            System.out.println(pmsBaseAttrInfoDto);
        }
    }

    @Test
    void test05() {
        String[] a = {"1", "2", "3"};
        HashSet<Long> b = new HashSet<>();
        b.add(1L);
        b.add(2L);
        Iterator<Long> iterator = b.iterator();
        while (iterator.hasNext()) {
            Long next = iterator.next();
            if (Arrays.asList(a).contains(next.toString())) {
                iterator.remove();
            }
        }
        for (Long aLong : b) {
            System.out.println(aLong);
        }
    }

    @Test
    void test06() throws JMSException {
        ActiveMQMapMessage activeMQMapMessage = new ActiveMQMapMessage();
        activeMQMapMessage.setString("name","shanqijie");
        mqProducerUtil.setMessage("test02",activeMQMapMessage);
    }

}
