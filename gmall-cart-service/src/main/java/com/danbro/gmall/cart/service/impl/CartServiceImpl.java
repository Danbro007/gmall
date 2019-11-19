package com.danbro.gmall.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.danbro.gmall.api.dto.OmsCartItemDto;
import com.danbro.gmall.api.service.CartService;
import com.danbro.gmall.cart.service.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Danrbo
 * @date 2019/10/29 12:13
 * description getCartListByMemberId
 **/
@Service
public class CartServiceImpl implements CartService {


    private CartMapper cartMapper;
    private RedisTemplate redisTemplate;

    public CartServiceImpl(CartMapper cartMapper, RedisTemplate redisTemplate) {
        this.cartMapper = cartMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public OmsCartItemDto getItemBySkuId(Long skuId, String memberId) {
        QueryWrapper<OmsCartItemDto> omsCartItemDtoQueryWrapper = new QueryWrapper<>();
        omsCartItemDtoQueryWrapper.eq("member_id", memberId).eq("product_sku_id", skuId);
        return cartMapper.selectOne(omsCartItemDtoQueryWrapper);

    }

    @Override
    public void updateItem(OmsCartItemDto updateOmsCartItemDto) {
        UpdateWrapper<OmsCartItemDto> omsCartItemDtoUpdateWrapper = new UpdateWrapper<>();
        omsCartItemDtoUpdateWrapper.eq("member_id", updateOmsCartItemDto.getMemberId()).eq("product_sku_id", updateOmsCartItemDto.getProductSkuId());
        int update = cartMapper.update(updateOmsCartItemDto, omsCartItemDtoUpdateWrapper);
    }

    @Override
    public void insert(OmsCartItemDto omsCartItemDto) {
        cartMapper.insert(omsCartItemDto);
    }


    @Override
    public void syncCartCache(String memberId) {
        //删除缓存
        redisTemplate.delete("User:" + memberId + ":cart");
        QueryWrapper<OmsCartItemDto> omsCartItemDtoQueryWrapper = new QueryWrapper<>();
        omsCartItemDtoQueryWrapper.eq("member_id", memberId);
        List<OmsCartItemDto> omsCartItemDtoList = cartMapper.selectList(omsCartItemDtoQueryWrapper);
        Map<Long, OmsCartItemDto> omsCartItemDtoMap = new HashMap<>(16);
        for (OmsCartItemDto omsCartItemDto : omsCartItemDtoList) {
            omsCartItemDtoMap.put(omsCartItemDto.getProductSkuId(), omsCartItemDto);
        }
        redisTemplate.opsForHash().putAll("User:" + memberId + ":cart", omsCartItemDtoMap);
    }

    @Override
    public List<OmsCartItemDto> getCartListByMemberId(String memberId) {
        Map<Long, OmsCartItemDto> dataFromCache = redisTemplate.opsForHash().entries("User:" + memberId + ":cart");
        //缓存为空 到数据库中取数据
        if (dataFromCache.isEmpty()) {
            QueryWrapper<OmsCartItemDto> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("member_id", memberId);
            List<OmsCartItemDto> omsCartItemDtoList = cartMapper.selectList(queryWrapper);
            if (omsCartItemDtoList != null) {
                //同步到缓存里
                Map<Long, OmsCartItemDto> omsCartItemDtoMap = new HashMap<>(16);
                for (OmsCartItemDto omsCartItemDto : omsCartItemDtoList) {
                    omsCartItemDtoMap.put(omsCartItemDto.getProductSkuId(), omsCartItemDto);
                }
                redisTemplate.opsForHash().putAll("User:" + memberId + ":cart", omsCartItemDtoMap);
            }
            return omsCartItemDtoList;
        } else {
            //缓存中的数据
            return new ArrayList<>(dataFromCache.values());
        }
    }

    @Transactional
    @Override
    public List<OmsCartItemDto> updateItemCart(OmsCartItemDto omsCartItemDto) {
        //获取缓存里的数据
        Map<Long, OmsCartItemDto> dataFromCache = redisTemplate.opsForHash().entries("User:" + omsCartItemDto.getMemberId() + ":cart");
        //清空缓存
        redisTemplate.delete("User:" + omsCartItemDto.getMemberId() + ":cart");
        //更新出商品状态
        UpdateWrapper<OmsCartItemDto> omsCartItemDtoUpdateWrapper = new UpdateWrapper<>();
        omsCartItemDtoUpdateWrapper.eq("member_id", omsCartItemDto.getMemberId()).eq("product_sku_id", omsCartItemDto.getProductSkuId());
        int update = cartMapper.update(omsCartItemDto, omsCartItemDtoUpdateWrapper);

        //更新缓存
        OmsCartItemDto omsCartItemDtoFromCache = dataFromCache.get(omsCartItemDto.getProductSkuId());
        //
        if (omsCartItemDtoFromCache != null) {
            if (omsCartItemDto.getQuantity() != null) {
                omsCartItemDtoFromCache.setQuantity(omsCartItemDto.getQuantity());
            }
            if (omsCartItemDto.getIsChecked() != null) {
                omsCartItemDtoFromCache.setIsChecked(omsCartItemDto.getIsChecked());
            }
            dataFromCache.put(omsCartItemDtoFromCache.getProductSkuId(), omsCartItemDtoFromCache);
        } else {
            System.out.println("异常");
        }
        redisTemplate.opsForHash().putAll("User:" + omsCartItemDto.getMemberId() + ":cart", dataFromCache);
        return new ArrayList<>(new TreeMap<>(dataFromCache).values());
    }
}
