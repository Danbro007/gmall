package com.danbro.gmall.order.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.dto.OmsCartItemDto;
import com.danbro.gmall.api.dto.OmsOrderDto;
import com.danbro.gmall.api.po.MemberReceiveAddressPo;
import com.danbro.gmall.api.po.OmsOrderItemPo;
import com.danbro.gmall.api.po.OmsOrderPo;
import com.danbro.gmall.api.service.CartService;
import com.danbro.gmall.api.service.MemberReceiveAddressService;
import com.danbro.gmall.api.service.OrderService;
import com.danbro.gmall.api.service.SkuService;
import com.danbro.gmall.cart.web.utils.ControllerUtil;
import com.danbro.gmall.common.utils.annotations.LoginRequired;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    @Reference
    OrderService orderService;

    @Reference
    SkuService skuService;


    @LoginRequired(successNecessary = true)
    @GetMapping("/toTrade")
    public String trade(HttpServletRequest request, Model model) {

        Long memberId = Long.parseLong((String) request.getAttribute("memberId"));
        String nickname = (String) request.getAttribute("nickname");
        List<OmsCartItemDto> orderDetailList = cartService.getCartListByMemberId(memberId, true);
        model.addAttribute("orderDetailList", orderDetailList);
        //地址列表
        List<MemberReceiveAddressPo> userAddressList = memberReceiveAddressService.selectAddressByMemberId(memberId);
        if (userAddressList != null) {
            model.addAttribute("userAddressList", userAddressList);
        }
        model.addAttribute("totalPrice", ControllerUtil.getTotalPrice(orderDetailList));
        String tradeCode = orderService.getTradeCode(memberId);
        model.addAttribute("tradeCode", tradeCode);
        return "trade";
    }

    @GetMapping("/tradeFail")
    public String tradeFail() {
        return "error";
    }


    @PostMapping("submitOrder")
    @LoginRequired(successNecessary = true)
    @Transactional(rollbackFor = Exception.class)
    public String submitOrder(HttpServletRequest request,
                              String tradeCode) {

        Long memberId = Long.parseLong((String) request.getAttribute("memberId"));
        Boolean success = orderService.checkTradeCode(memberId, tradeCode);

        if (!success){
            return "tradeFail";
        }
        //创建订单
        OmsOrderDto omsOrderDto = new OmsOrderDto();
        //订单里的商品列表
        List<OmsOrderItemPo> omsOrderItemPoList = new ArrayList<>();
        //订单自动确认日期
        omsOrderDto.setAutoConfirmDay(7);
        //不开发票
        omsOrderDto.setBillType(0);
        //订单确认状态
        omsOrderDto.setConfirmStatus(0);
        //订单的用户id
        omsOrderDto.setMemberId(memberId);
        //顶大备注
        omsOrderDto.setBillContent("订单备注");
        //订单联系电话
        omsOrderDto.setBillReceiverPhone("131222809765");
        //订单创建时间
        omsOrderDto.setCreateTime(new Date());

        omsOrderDto.setDeleteStatus(0);

        //订单地址详细
        MemberReceiveAddressPo memberReceiveAddressPo = memberReceiveAddressService.selectDefaultAddressByMemberId(memberId);
        omsOrderDto.setReceiverDetailAddress(memberReceiveAddressPo.getDetailAddress());
        omsOrderDto.setReceiverCity(memberReceiveAddressPo.getCity());
        omsOrderDto.setReceiverName(memberReceiveAddressPo.getName());
        omsOrderDto.setReceiverPhone(memberReceiveAddressPo.getPhoneNumber());
        omsOrderDto.setReceiverPostCode(memberReceiveAddressPo.getPostCode());
        omsOrderDto.setReceiverProvince(memberReceiveAddressPo.getProvince());
        omsOrderDto.setReceiverRegion(memberReceiveAddressPo.getRegion());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,7);
        omsOrderDto.setReceiveTime(calendar.getTime());

        List<OmsCartItemDto> cartList = cartService.getCartListByMemberId(memberId, true);
        for (OmsCartItemDto omsCartItemDto : cartList) {
            Boolean flag = skuService.checkSkuPrice(omsCartItemDto.getProductSkuId(), omsCartItemDto.getProductPrice());
            //价格校验失败 跳转到交易失败页面
            if (!flag){
                return "tradeFail";
            }
            OmsOrderItemPo omsOrderItemPo = new OmsOrderItemPo();
            omsOrderItemPo.setProductAttr(omsCartItemDto.getProductAttr());
            omsOrderItemPo.setProductBrand(omsCartItemDto.getProductBrand());
            omsOrderItemPo.setProductCategoryId(omsCartItemDto.getProductCategoryId());
            omsOrderItemPo.setProductName(omsCartItemDto.getProductName());
            omsOrderItemPo.setProductPrice(omsCartItemDto.getProductPrice());
            omsOrderItemPo.setSp1(omsCartItemDto.getSp1());
            omsOrderItemPo.setSp2(omsCartItemDto.getSp2());
            omsOrderItemPo.setSp3(omsCartItemDto.getSp3());
            omsOrderItemPo.setProductPic(omsCartItemDto.getProductPic());
            omsOrderItemPo.setProductSkuId(omsCartItemDto.getProductSkuId());
            omsOrderItemPo.setProductId(omsCartItemDto.getProductId());
            omsOrderItemPo.setProductQuantity(omsCartItemDto.getQuantity());
            omsOrderItemPo.setProductSkuCode(omsCartItemDto.getProductSkuCode());
            omsOrderItemPo.setProductSn(omsCartItemDto.getProductSn());

            omsOrderItemPoList.add(omsOrderItemPo);
        }
        omsOrderDto.setOmsOrderItemPoList(omsOrderItemPoList);
        omsOrderDto.setTotalAmount(ControllerUtil.getTotalPrice(cartList));
        //保存订单
        orderService.saveOrder(omsOrderDto);

        return "success";
    }

}
