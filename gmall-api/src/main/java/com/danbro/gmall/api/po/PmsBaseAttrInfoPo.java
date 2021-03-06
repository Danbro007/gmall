package com.danbro.gmall.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Danrbo
 * @date 2019/9/10 14:17
 * description
 **/
@Data
public class PmsBaseAttrInfoPo implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String attrName;
    private Long catalog3Id;
    private String isEnabled;

}
