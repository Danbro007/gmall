package com.danbro.gmall.api.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Danrbo
 * @date 2019/9/5 15:42
 * description
 **/
@Data
@TableName(value = "ums_member")
public class Member implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long memberLevelId;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private Integer status;
    private Date createTime;
    private String icon;
    private Integer gender;
    private Date birthday;
    private String city;
    private String job;
    private String personalizedSignature;
    private Integer sourceType;
    private Integer integration;
    private Integer growth;
    private Integer luckeyCount;
    private Integer historyIntegration;
}