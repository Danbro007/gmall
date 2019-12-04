package com.danbro.gmall.cart.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.danbro.gmall.api.dto.OmsCartItemDto;
import com.danbro.gmall.api.dto.PmsSkuInfoDto;
import com.danbro.gmall.api.service.CartService;
import com.danbro.gmall.api.service.SkuService;
import com.danbro.gmall.cart.web.utils.ControllerUtil;

import com.danbro.gmall.common.utils.annotations.LoginRequired;
import com.danbro.gmall.common.utils.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/28 15:28
 * description
 **/
@Controller
public class CartController {

    @Reference
    CartService cartService;

    @Reference
    SkuService skuService;

    @LoginRequired
    @PostMapping("/addToCart")

    public String addToCart(@Param("quantity") Integer quantity,
                            @Param("skuId") Long skuId,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            Model model) {
        //通过skuId到数据库中查询商品信息
        PmsSkuInfoDto skuInfo = skuService.getSkuById(skuId);
        //建立购物车里的商品对象
        OmsCartItemDto omsCartItemDto = ControllerUtil.getOmsCartItemDto(skuInfo, quantity);
        omsCartItemDto.setIsChecked(1);
        String memberId = (String) request.getAttribute("memberId");
        //用户未登录
        if (StringUtils.isBlank(memberId)) {
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            //cartListCookie为空
            List<OmsCartItemDto> omsCartItemList = new ArrayList<>();
            if (StringUtils.isBlank(cartListCookie)) {
                omsCartItemList.add(omsCartItemDto);
            }
            //cartListCookie不为空
            else {
                omsCartItemList = JSON.parseArray(cartListCookie, OmsCartItemDto.class);
                boolean exist = ControllerUtil.ifCartExist(omsCartItemList, omsCartItemDto);
                //要添加的商品在购物车里有则添加相应的数量
                if (exist) {
                    for (OmsCartItemDto cartItemDtoFromCookie : omsCartItemList) {
                        if (cartItemDtoFromCookie.getProductSkuId().equals(omsCartItemDto.getProductSkuId())) {
                            cartItemDtoFromCookie.setQuantity(cartItemDtoFromCookie.getQuantity() + omsCartItemDto.getQuantity());
                            cartItemDtoFromCookie.setProductPrice(cartItemDtoFromCookie.getProductPrice().add(omsCartItemDto.getProductPrice()));
                        }
                    }
                }
                //要添加的商品不在购物车里则添加进购物车里
                else {
                    omsCartItemList.add(omsCartItemDto);
                }
            }
            //更新cookie
            CookieUtil.setCookie(request, response, "cartListCookie", JSON.toJSONString(omsCartItemList), 60 * 60 * 24 * 3, true);
        }
        //用户已经登录
        else {
            omsCartItemDto.setMemberId(memberId);
            OmsCartItemDto cartItemDtoFromDb = cartService.getItemBySkuId(skuId, memberId);
            //购物车里有此商品则更新数量
            if (cartItemDtoFromDb != null) {
                cartItemDtoFromDb.setQuantity(cartItemDtoFromDb.getQuantity() + quantity);
                cartService.updateItem(cartItemDtoFromDb);
            } else {
                cartService.insert(omsCartItemDto);
            }
            cartService.syncCartCache(memberId);
        }
        model.addAttribute("skuInfo", omsCartItemDto);
        return "redirect:/success.html";
    }


    @GetMapping("/cartList")
    @LoginRequired
    public String cartList(HttpServletRequest request, Model model) {
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickName");

        List<OmsCartItemDto> cartList = new ArrayList<>();
        if (memberId == null) {
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            List<OmsCartItemDto> omsCartItemDtoListFromCookie = JSON.parseArray(cartListCookie, OmsCartItemDto.class);
            if (omsCartItemDtoListFromCookie != null) {
                cartList.addAll(omsCartItemDtoListFromCookie);
            }
        } else {
            //登录
            List<OmsCartItemDto> omsCartItemDtoList = cartService.getCartListByMemberId(Long.parseLong(memberId), false);
            cartList.addAll(omsCartItemDtoList);
        }
        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPrice", ControllerUtil.getTotalPrice(cartList));
        return "cartList";
    }

    @PostMapping("/checkCart")
    public String checkCart(@Param("skuId") Long skuId,
                            @Param("isChecked") Integer isChecked,
                            @Param("quantity") Integer quantity,
                            Model model,
                            HttpServletRequest request) {
        String memberId = (String) request.getAttribute("memberId");
        OmsCartItemDto omsCartItemDto = new OmsCartItemDto();
        omsCartItemDto.setMemberId(memberId);
        omsCartItemDto.setProductSkuId(skuId);
        if (isChecked != null) {
            omsCartItemDto.setIsChecked(isChecked);
        }
        if (quantity != null) {
            omsCartItemDto.setQuantity(quantity);
        }
        List<OmsCartItemDto> cartList = cartService.updateItemCart(omsCartItemDto);
        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPrice", ControllerUtil.getTotalPrice(cartList));
        return "cartListInner";
    }

}
