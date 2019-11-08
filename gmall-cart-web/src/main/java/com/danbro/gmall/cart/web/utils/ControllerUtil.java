package com.danbro.gmall.cart.web.utils;

import com.danbro.gmall.api.dto.OmsCartItemDto;
import com.danbro.gmall.api.dto.PmsSkuInfoDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/30 14:35
 * description
 **/
public class ControllerUtil {

    /**
     * 判断商品是否在cookie里
     *
     * @param omsCartItemDtoList cookie里存储的商品列表
     * @param omsCartItemDto     商品
     * @return 存在true or 不存在false
     */
    public static boolean ifCartExist(List<OmsCartItemDto> omsCartItemDtoList, OmsCartItemDto omsCartItemDto) {
        for (OmsCartItemDto cartItemDto : omsCartItemDtoList) {
            if (cartItemDto.getProductSkuId().equals(omsCartItemDto.getProductSkuId())) {
                return true;
            }
        }
        return false;
    }

    public static OmsCartItemDto getOmsCartItemDto(PmsSkuInfoDto skuInfo, Integer quantity) {
        OmsCartItemDto omsCartItemDto = new OmsCartItemDto();
        omsCartItemDto.setCreateDate(new Date());
        omsCartItemDto.setModifyDate(new Date());
        omsCartItemDto.setProductId(skuInfo.getProductId());
        omsCartItemDto.setProductSkuId(skuInfo.getId());
        omsCartItemDto.setPrice(skuInfo.getPrice());
        omsCartItemDto.setProductName(skuInfo.getSkuName());
        omsCartItemDto.setProductCategoryId(skuInfo.getCatalog3Id());
        omsCartItemDto.setProductPic(skuInfo.getSkuDefaultImg());
        omsCartItemDto.setDeleteStatus(false);
        omsCartItemDto.setQuantity(quantity);
        return omsCartItemDto;
    }

    /**
     * 获得购物车里所有商品的总价
     *
     * @param cartList 购物车商品列表
     * @return 商品总价
     */
    public static BigDecimal getTotalPrice(List<OmsCartItemDto> cartList) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        if (cartList.size() <= 0){
            return totalPrice;
        }
        for (OmsCartItemDto omsCartItemDto : cartList) {
            if (omsCartItemDto.getIsChecked().equals(1)) {
                BigDecimal quantity = new BigDecimal(omsCartItemDto.getQuantity());
                BigDecimal total = quantity.multiply(omsCartItemDto.getPrice());
                totalPrice = totalPrice.add(total);
            }
        }
        return totalPrice;
    }

}
