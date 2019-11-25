package com.danbro.gmall.api.service;

import com.danbro.gmall.api.dto.OmsCartItemDto;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/28 16:05
 * description 购物车service
 **/
public interface CartService {

    /**
     * 通过memberId和skuId找到相关用户购物车里的商品信息
     *
     * @param skuId    商品Id
     * @param memberId 用户Id
     * @return 购物车里的商品信息
     */
    OmsCartItemDto getItemBySkuId(Long skuId, String memberId);

    /**
     * 通过skuId和memberId更新购物车里的商品信息
     *
     * @param updateOmsCartItemDto 更新的商品信息
     */
    void updateItem(OmsCartItemDto updateOmsCartItemDto);

    /**
     * 通过memberId在相关用户的购物车里添加商品
     *
     * @param omsCartItemDto 商品信息
     */
    void insert(OmsCartItemDto omsCartItemDto);

    /**
     * 把数据里购物车的信息同步到缓存里
     *
     * @param memberId 用户Id
     */
    void syncCartCache(String memberId);


    /**
     * 通过用户Id从缓存里获取用户的购物车商品列表
     * @param memberId 用户Id
     * @param isChecked 是否过滤出已购物车里已经勾选的商品 true :显示已勾选的商品 false:显示购物车里所有的商品
     * @return 购物车里的商品列表
     */
    List<OmsCartItemDto> getCartListByMemberId(Long memberId,Boolean isChecked);

    /**
     * 更新购物车里的商品信息
     * @param omsCartItemDto 购物车里的单个商品
     * @return 更新后购物车商品列表
     */
    List<OmsCartItemDto> updateItemCart(OmsCartItemDto omsCartItemDto);


    /**
     * 删除用户购物车里的商品
     * @param memberId 用户id
     * @param skuId 商品id
     */
    void deleteCartItem(String memberId,Long skuId);





}
