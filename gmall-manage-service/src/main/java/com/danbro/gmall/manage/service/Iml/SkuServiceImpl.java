package com.danbro.gmall.manage.service.Iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.danbro.gmall.api.bean.PmsSkuAttrValue;
import com.danbro.gmall.api.bean.PmsSkuImage;
import com.danbro.gmall.api.bean.PmsSkuInfo;
import com.danbro.gmall.api.bean.PmsSkuSaleAttrValue;
import com.danbro.gmall.api.service.PmsSkuService;
import com.danbro.gmall.manage.service.mapper.PmsSkuAttrValueMapper;
import com.danbro.gmall.manage.service.mapper.PmsSkuImageMapper;
import com.danbro.gmall.manage.service.mapper.PmsSkuInfoMapper;
import com.danbro.gmall.manage.service.mapper.PmsSkuSaleAttrValueMapper;
import com.danbro.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

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
    private RedisUtil redisUtil;
    private RedissonClient redissonClient;


    public SkuServiceImpl(PmsSkuInfoMapper pmsSkuInfoMapper, PmsSkuAttrValueMapper pmsSkuAttrValueMapper, PmsSkuImageMapper pmsSkuImageMapper, PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper, RedisUtil redisUtil, RedissonClient redissonClient) {
        this.pmsSkuInfoMapper = pmsSkuInfoMapper;
        this.pmsSkuAttrValueMapper = pmsSkuAttrValueMapper;
        this.pmsSkuImageMapper = pmsSkuImageMapper;
        this.pmsSkuSaleAttrValueMapper = pmsSkuSaleAttrValueMapper;
        this.redisUtil = redisUtil;
        this.redissonClient = redissonClient;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSkuInfo(PmsSkuInfo pmsSkuInfo) {
        int flag = pmsSkuInfoMapper.insert(pmsSkuInfo);
        if (flag == 1) {
            for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuInfo.getSkuAttrValueList()) {
                pmsSkuAttrValue.setSkuId(pmsSkuInfo.getId());
                pmsSkuAttrValueMapper.insert(pmsSkuAttrValue);
                for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuAttrValue.getSkuSaleAttrValueList()) {
                    pmsSkuSaleAttrValue.setSkuId(pmsSkuInfo.getId());
                    pmsSkuSaleAttrValueMapper.insert(pmsSkuSaleAttrValue);
                }
            }

            for (PmsSkuImage pmsSkuImage : pmsSkuInfo.getSkuImageList()) {
                pmsSkuImage.setSkuId(pmsSkuInfo.getId());
                pmsSkuImageMapper.insert(pmsSkuImage);
            }
        }
        return flag;
    }

    @Override
    public PmsSkuInfo getSkuFromDb(Long skuId) {
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectById(skuId);
        HashMap<String, Object> imageColumnMap = new HashMap<>(16);
        imageColumnMap.put("sku_id", skuId);
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.selectByMap(imageColumnMap);
        pmsSkuInfo.setSkuImageList(pmsSkuImages);
        return pmsSkuInfo;
    }

    @Override
    public String selectSkuSaleAttrListCheckBySpu(Long spuId) {
        HashMap<String, String> pmsSkuInfoMap = new HashMap<>(16);
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuSaleAttrListCheckBySpu(spuId);
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
            String key = "";
            for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuInfo.getSkuAttrValueList()) {
                List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuAttrValue.getSkuSaleAttrValueList();
                for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
                    if (StringUtils.isEmpty(key)) {
                        key += pmsSkuSaleAttrValue.getSaleAttrValueId();
                    } else {
                        key += "|" + pmsSkuSaleAttrValue.getSaleAttrValueId();
                    }
                }
            }
            pmsSkuInfoMap.put(key, pmsSkuInfo.getId().toString());
        }
        return JSON.toJSONString(pmsSkuInfoMap);
    }

    @Override
    public PmsSkuInfo getSkuById(Long skuId) {
        Jedis jedis = redisUtil.getJedis();
        RLock lock = redissonClient.getLock("lock");
        lock.lock();
        try {
            String skuKey = "sku:" + skuId +":info";
            String skuVal = jedis.get(skuKey);
            if (StringUtils.isNotBlank(skuVal)){
                return JSON.parseObject(skuVal,PmsSkuInfo.class);
            }else {
                PmsSkuInfo skuFromDb = getSkuFromDb(skuId);
                if (skuFromDb != null){
                    jedis.set(skuKey, JSON.toJSONString(skuFromDb));
                }else {
                    jedis.setex(skuKey,60*5, JSON.toJSONString(""));
                }
                return skuFromDb;
            }
        } finally {
            lock.unlock();
            jedis.close();
        }
    }
}
