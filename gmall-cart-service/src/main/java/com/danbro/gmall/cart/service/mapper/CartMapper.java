package com.danbro.gmall.cart.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.danbro.gmall.api.dto.OmsCartItemDto;
import org.springframework.stereotype.Component;

/**
 * @author Danrbo
 * @date 2019/10/29 12:12
 * description
 **/
public interface CartMapper extends BaseMapper<OmsCartItemDto> {

    /**
     *更新购物车里的商品数量
     * @param omsCartItemDto 要更新的购物车商品信息
     * @return 更新是够成功 1：成功， 0：失败
     */
    int updateCartItemQuantity(OmsCartItemDto omsCartItemDto);

}
