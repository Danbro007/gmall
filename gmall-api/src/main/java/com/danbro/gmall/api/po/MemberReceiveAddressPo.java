package com.danbro.gmall.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Danrbo
 * @date 2019/9/5 21:30
 * description
 **/
@TableName(value = "ums_member_receive_address")
@Data
public class MemberReceiveAddressPo implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Long memberId;
    private String name;
    private String phoneNumber;
    private int defaultStatus;
    private String postCode;
    private String province;
    private String city;
    private String region;
    private String detailAddress;

}
