package com.danbro.gmall.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Danrbo
 * @date 2019/9/10 14:17
 * description
 **/
@Data
@TableName(value = "oms_company_address")
public class OmsCompanyAddressPo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String addressName;
    private boolean sendStatus;
    private boolean receiveStatus;
    private String name;
    private String phone;
    private String province;
    private String city;
    private String region;
    private String detailAddress;


}
