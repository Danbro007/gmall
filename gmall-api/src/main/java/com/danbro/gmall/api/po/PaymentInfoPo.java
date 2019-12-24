package com.danbro.gmall.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @Classname PaymentInfoPO
 * @Description TODO 支付数据表
 * @Date 2019/11/29 14:40
 * @Author Danrbo
 */
@Data
@TableName(value = "payment_info")
public class PaymentInfoPo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long  id;
    private Long orderId;
    private String orderSn;
    private String alipayTradeNo;
    private BigDecimal totalAmount;
    private String subject;
    private Boolean paymentStatus;
    private Date createTime;
    private Date callbackTime;
    private String callbackContent;

}
