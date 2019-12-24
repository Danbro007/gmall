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
import com.danbro.gmall.common.utils.config.AlipayConfig;
import com.danbro.gmall.common.utils.exceptions.CustomizeErrorCode;
import com.danbro.gmall.common.utils.exceptions.CustomizeException;
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
                        String orderSn,
                        Model model) {
        Long memberId = Long.parseLong((String) request.getAttribute("memberId"));
        OmsOrderDto omsOrderDto = orderService.selectOrderByOrderSn(orderSn);
        model.addAttribute("totalAmount", omsOrderDto.getTotalAmount());
        model.addAttribute("orderSn", omsOrderDto.getOrderSn());
        return "index";
    }

    @LoginRequired(successNecessary = true)
    @PostMapping("/alipay/submit")
    @ResponseBody
    public String alipayPay(HttpServletRequest request,
                            String orderSn,
                            BigDecimal totalAmount) {
        Long memberId = Long.parseLong((String) request.getAttribute("memberId"));
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.returnPaymentUrl);
        alipayRequest.setNotifyUrl(AlipayConfig.notifyPaymentUrl);

        HashMap<String, String> map = new HashMap<>(16);
        map.put("out_trade_no", orderSn);
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

        OmsOrderDto omsOrderDto = orderService.selectOrderByOrderSn(orderSn);
        //创建支付记录
        PaymentInfoPo paymentInfo = new PaymentInfoPo();
        paymentInfo.setOrderSn(orderSn);
        paymentInfo.setTotalAmount(totalAmount);
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setPaymentStatus(false);
        paymentInfo.setOrderId(omsOrderDto.getId());
        paymentService.insert(paymentInfo);
        //通过延迟队列检查当前订单的支付状态
        paymentService.checkPaymentSuccessQueue(orderSn,5);
        return form;
    }

    @LoginRequired(successNecessary = true)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/alipay/callback/return")
    public String alipayCallbackReturn(AlipayReturnParamVo alipayReturnParamVo,
                                       HttpServletRequest request) {

        if (alipayReturnParamVo.getSign() != null) {
            //验证签名成功
            PaymentInfoPo paymentInfoFromDb = paymentService.selectPaymentByTradeNoCode(alipayReturnParamVo.getOutTradeNo());
            if (paymentInfoFromDb == null){
                throw new CustomizeException(CustomizeErrorCode.PAYMENT_NOT_FOUND);
            }

            paymentInfoFromDb.setAlipayTradeNo(alipayReturnParamVo.getTradeNo());
            paymentInfoFromDb.setCallbackTime(paymentInfoFromDb.getCreateTime());
            paymentInfoFromDb.setOrderSn(alipayReturnParamVo.getOutTradeNo());
            paymentInfoFromDb.setPaymentStatus(true);
            paymentInfoFromDb.setCallbackContent(request.getQueryString());
            paymentInfoFromDb.setSubject("支付测试");
            //更新订单状态
            paymentService.updatePayment(paymentInfoFromDb);

        }
        return "支付成功";
    }


    @LoginRequired(successNecessary = true)
    @GetMapping("/wechat/submit")
    public String wechatPay() {
        return "";
    }

}
