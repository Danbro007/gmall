package com.danbro.gmall.order.web.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtil {
    /**
     * 创建订单编号
     * @return 订单编号
     */
    public static String createOrderSn(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmSS");
        String timeFormat = simpleDateFormat.format(new Date());
        return "gmall" + timeFormat + RandomStringUtils.randomNumeric(6);
    }

}
