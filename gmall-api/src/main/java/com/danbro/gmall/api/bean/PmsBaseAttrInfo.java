package com.danbro.gmall.api.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 14:17
 * description
 **/
@TableName(value = "pms_base_attr_info")
@Data
public class PmsBaseAttrInfo implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String attrName;
    private Long catalog3Id;
    private String isEnabled;
    @TableField(exist = false)
    List<PmsBaseAttrValue> attrValueList;
}
