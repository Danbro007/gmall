package com.danbro.gmall.api.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Danrbo
 * @date 2019/9/10 14:17
 * description
 **/
@TableName(value = "pms_base_attr_info")
@Data
public class PmsBaseAttrInfo implements Serializable {
    @TableId
    private Long id;
    private String attrName;
    private Long catalog3Id;
    private String isEnabled;
}
