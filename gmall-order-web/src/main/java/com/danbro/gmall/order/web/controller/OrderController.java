package com.danbro.gmall.order.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.dto.OmsCartItemDto;
import com.danbro.gmall.api.po.MemberReceiveAddressPo;
import com.danbro.gmall.api.service.CartService;
import com.danbro.gmall.api.service.MemberReceiveAddressService;
import com.danbro.gmall.cart.web.utils.ControllerUtil;
import com.danbro.gmall.web.utils.annotations.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/11/17 10:42
 * description
 **/
@Controller
public class OrderController {

    @Reference
    CartService cartService;
    @Reference
    MemberReceiveAddressService memberReceiveAddressService;


    @LoginRequired(successNecessary = true)
    @GetMapping("/toTrade")
    public String trade(HttpServletRequest request, Model model) {

        String memberId = (String)request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        List<OmsCartItemDto> cartListFromDb = cartService.getCartListByMemberId(memberId);
        ArrayList<OmsCartItemDto> orderDetailList = new ArrayList<>();
        if (cartListFromDb != null){
            for (OmsCartItemDto omsCartItemDto : cartListFromDb) {
                if (omsCartItemDto.getIsChecked() == 1){
                    orderDetailList.add(omsCartItemDto);
                }
            }
        }
        model.addAttribute("orderDetailList",orderDetailList);

        List<MemberReceiveAddressPo> userAddressList = memberReceiveAddressService.selectAddressByMemberId(Long.parseLong(memberId));
        if (userAddressList != null){
            model.addAttribute("userAddressList",userAddressList);
        }
        model.addAttribute("totalPrice", ControllerUtil.getTotalPrice(orderDetailList));

        return "trade";
    }

}
