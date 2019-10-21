package com.danbro.gmall.api.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.danbro.gmall.api.po.PmsBaseAttrInfoPo;
import lombok.Data;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/18 13:47
 * description
 **/

@Data
@TableName(value = "pms_base_attr_info")
public class PmsBaseAttrInfoDto extends PmsBaseAttrInfoPo {
    @TableField(exist = false)
    List<PmsBaseAttrValueDto> attrValueList;
}
