package com.danbro.gmall.manage.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.danbro.gmall.api.dto.PmsSkuAttrValueDto;
import com.danbro.gmall.api.dto.PmsSkuImageDto;
import com.danbro.gmall.api.dto.PmsSkuInfoDto;
import com.danbro.gmall.api.dto.PmsSkuSaleAttrValueDto;
import com.danbro.gmall.api.service.PmsSkuService;
import com.danbro.gmall.api.vo.PmsSkuInfoVo;
import com.danbro.gmall.manage.service.mapper.PmsSkuAttrValueMapper;
import com.danbro.gmall.manage.service.mapper.PmsSkuImageMapper;
import com.danbro.gmall.manage.service.mapper.PmsSkuInfoMapper;
import com.danbro.gmall.manage.service.mapper.PmsSkuSaleAttrValueMapper;
import com.danbro.gmall.web.utils.bean.Result;
import com.danbro.gmall.web.utils.enums.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/17 18:55
 * description
 **/
@Service
@CacheConfig(cacheNames = "sku")
public class SkuServiceImpl implements PmsSkuService {

    private PmsSkuInfoMapper pmsSkuInfoMapper;
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    private PmsSkuImageMapper pmsSkuImageMapper;
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    private RedissonClient redissonClient;

    public SkuServiceImpl(PmsSkuInfoMapper pmsSkuInfoMapper, PmsSkuAttrValueMapper pmsSkuAttrValueMapper, PmsSkuImageMapper pmsSkuImageMapper, PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper, RedissonClient redissonClient, StringRedisTemplate stringRedisTemplate) {
        this.pmsSkuInfoMapper = pmsSkuInfoMapper;
        this.pmsSkuAttrValueMapper = pmsSkuAttrValueMapper;
        this.pmsSkuImageMapper = pmsSkuImageMapper;
        this.pmsSkuSaleAttrValueMapper = pmsSkuSaleAttrValueMapper;
        this.redissonClient = redissonClient;
    }

    @Autowired
    RedisTemplate redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSkuInfo(PmsSkuInfoVo pmsSkuInfoVo) {
        PmsSkuInfoDto pmsSkuInfoDto = new PmsSkuInfoDto();
        BeanUtils.copyProperties(pmsSkuInfoVo, pmsSkuInfoDto);
        int flag = pmsSkuInfoMapper.insert(pmsSkuInfoDto);
        if (flag == 1) {
            List<PmsSkuAttrValueDto> skuAttrValueList = pmsSkuInfoDto.getSkuAttrValueList();
            List<PmsSkuSaleAttrValueDto> skuSaleAttrValueList = pmsSkuInfoDto.getSkuSaleAttrValueList();
            List<PmsSkuImageDto> skuImageList = pmsSkuInfoDto.getSkuImageList();
            if (skuAttrValueList.size() > 0) {
                for (PmsSkuAttrValueDto pmsSkuAttrValueDto : skuAttrValueList) {
                    pmsSkuAttrValueDto.setSkuId(pmsSkuInfoDto.getId());
                    pmsSkuAttrValueMapper.insert(pmsSkuAttrValueDto);
                }
            }
            if (skuSaleAttrValueList.size() > 0) {
                for (PmsSkuSaleAttrValueDto pmsSkuSaleAttrValueDto : skuSaleAttrValueList) {
                    pmsSkuSaleAttrValueDto.setSkuId(pmsSkuInfoDto.getId());
                    pmsSkuSaleAttrValueMapper.insert(pmsSkuSaleAttrValueDto);
                }
            }
            if (skuImageList.size() > 0){
                for (PmsSkuImageDto pmsSkuImageDto : skuImageList) {
                    pmsSkuImageDto.setSkuId(pmsSkuInfoDto.getId());
                    pmsSkuImageMapper.insert(pmsSkuImageDto);
                }
            }

        }
        return flag;
    }

    @Override
    public PmsSkuInfoDto getSkuFromDb(Long skuId) {
        PmsSkuInfoDto pmsSkuInfoDto = pmsSkuInfoMapper.selectById(skuId);
        if (pmsSkuInfoDto == null){
            return null;
        }
        HashMap<String, Object> imageColumnMap = new HashMap<>(16);
        imageColumnMap.put("sku_id", skuId);
        List<PmsSkuImageDto> pmsSkuImageDtoList = pmsSkuImageMapper.selectByMap(imageColumnMap);
        pmsSkuInfoDto.setSkuImageList(pmsSkuImageDtoList);
        return pmsSkuInfoDto;
    }

    @Override
    public String selectSkuSaleAttrListCheckBySpu(Long spuId) {
        HashMap<String, String> pmsSkuInfoMap = new HashMap<>(16);
        List<PmsSkuInfoDto> pmsSkuInfoDtoList = pmsSkuInfoMapper.selectSkuSaleAttrListCheckBySpu(spuId);
        for (PmsSkuInfoDto pmsSkuInfoDto : pmsSkuInfoDtoList) {
            String key = "";
            List<PmsSkuSaleAttrValueDto> skuSaleAttrValueList = pmsSkuInfoDto.getSkuSaleAttrValueList();
            for (PmsSkuSaleAttrValueDto pmsSkuSaleAttrValueDto : skuSaleAttrValueList) {
                if (StringUtils.isEmpty(key)) {
                    key += pmsSkuSaleAttrValueDto.getSaleAttrValueId();
                } else {
                    key += "|" + pmsSkuSaleAttrValueDto.getSaleAttrValueId();
                }
            }
            pmsSkuInfoMap.put(key, pmsSkuInfoDto.getId().toString());

        }
        return JSON.toJSONString(pmsSkuInfoMap);

    }


    @Override
    public PmsSkuInfoDto getSkuById(Long skuId) {
        RLock lock = redissonClient.getLock("lock");
        lock.lock();
        try {
            String skuKey = "sku:" + skuId + ":info";
            Object pmsSkuInfoDto = redisTemplate.opsForValue().get(skuKey);
            //从缓存获取 没有的话到数据库获取
            if (pmsSkuInfoDto != null){
                return (PmsSkuInfoDto) pmsSkuInfoDto;
            } else {
                PmsSkuInfoDto skuFromDb = getSkuFromDb(skuId);
                if (skuFromDb != null) {
                    redisTemplate.opsForValue().set(skuKey,skuFromDb);
                } else {

                }
                return skuFromDb;
            }

        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<PmsSkuInfoDto> getAllSku(Long catalog3Id) {
        HashMap<String, Object> pmsSkuInfoColumnMap = new HashMap<>(16);
        pmsSkuInfoColumnMap.put("catalog3_id", catalog3Id);
        List<PmsSkuInfoDto> pmsSkuInfoDtoList = pmsSkuInfoMapper.selectByMap(pmsSkuInfoColumnMap);
        for (PmsSkuInfoDto pmsSkuInfoDto : pmsSkuInfoDtoList) {
            PmsSkuAttrValueDto pmsSkuAttrValueDto = new PmsSkuAttrValueDto();
            pmsSkuAttrValueDto.setSkuId(pmsSkuInfoDto.getId());
            HashMap<String, Object> columnMap = new HashMap<>(16);
            columnMap.put("sku_id", pmsSkuInfoDto.getId());
            List<PmsSkuAttrValueDto> pmsSkuAttrValueDtoList = pmsSkuAttrValueMapper.selectByMap(columnMap);
            pmsSkuInfoDto.setSkuAttrValueList(pmsSkuAttrValueDtoList);
        }
        return pmsSkuInfoDtoList;
    }
}
