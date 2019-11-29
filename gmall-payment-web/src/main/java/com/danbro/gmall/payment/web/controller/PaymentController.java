package com.danbro.gmall.payment.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.danbro.gmall.api.dto.OmsOrderDto;
import com.danbro.gmall.api.po.PaymentInfoPo;
import com.danbro.gmall.api.service.OrderService;
import com.danbro.gmall.api.service.PaymentService;
import com.danbro.gmall.api.vo.AlipayReturnParamVo;
import com.danbro.gmall.common.utils.annotations.LoginRequired;
import com.danbro.gmall.payment.web.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

@Controller
public class PaymentController {


    @Autowired
    AlipayClient alipayClient;
    @Reference
    OrderService orderService;
    @Reference
    PaymentService paymentService;

    @LoginRequired(successNecessary = true)
    @GetMapping("/index")
    public String index(HttpServletRequest request,
                        String orderId,
                        Model model) {
        Long memberId = Long.parseLong((String) request.getAttribute("memberId"));
        OmsOrderDto omsOrderDto = orderService.selectOrderByOrderSn(orderId);
        model.addAttribute("totalAmount", omsOrderDto.getTotalAmount());
        model.addAttribute("orderId", omsOrderDto.getOrderSn());
        return "index";
    }

    @LoginRequired(successNecessary = true)
    @PostMapping("/alipay/submit")
    @ResponseBody
    public String alipayPay(HttpServletRequest request,
                            String orderId,
                            BigDecimal totalAmount) {
        Long memberId = Long.parseLong((String) request.getAttribute("memberId"));
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.returnPaymentUrl);
        alipayRequest.setNotifyUrl(AlipayConfig.notifyPaymentUrl);

        HashMap<String, String> map = new HashMap<>(16);
        map.put("out_trade_no", orderId);
        map.put("product_code", "FAST_INSTANT_TRADE_PAY");
        map.put("total_amount", "0.01");
        map.put("subject", "小米手机测试");
        String param = JSON.toJSONString(map);
        alipayRequest.setBizContent(param);
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.println(form);
        return form;
    }

    @LoginRequired(successNecessary = true)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/alipay/callback/return")
    public String alipayCallbackReturn(AlipayReturnParamVo alipayReturnParamVo,
                                       HttpServletRequest request){

        if (alipayReturnParamVo.getSign() != null){
            //验证签名成功
            OmsOrderDto omsOrderDto = orderService.selectOrderByOrderSn(alipayReturnParamVo.getOutTradeNo());
            PaymentInfoPo paymentInfo = new PaymentInfoPo();
            paymentInfo.setAlipayTradeNo(alipayReturnParamVo.getTradeNo());
            paymentInfo.setCreateTime(new Date());
            paymentInfo.setCallbackTime(paymentInfo.getCreateTime());
            paymentInfo.setOrderSn(alipayReturnParamVo.getOutTradeNo());
            paymentInfo.setPaymentStatus(true);
            paymentInfo.setOrderId(omsOrderDto.getId());
            paymentInfo.setTotalAmount(alipayReturnParamVo.getTotalAmount());
            paymentInfo.setCallbackContent(request.getQueryString());
            paymentInfo.setSubject("支付测试");
            paymentService.insert(paymentInfo);
            omsOrderDto.setPayAmount(alipayReturnParamVo.getTotalAmount());
            omsOrderDto.setPayType(1);
            omsOrderDto.setStatus(1);
            omsOrderDto.setPaymentTime(paymentInfo.getCreateTime());
            int i = orderService.updateOrder(omsOrderDto);
            System.out.println(i);

        }
        return "支付成功";
    }


    @LoginRequired(successNecessary = true)
    @GetMapping("/wechat/submit")
    public String wechatPay() {
        return "";
    }

}
