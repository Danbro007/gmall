package com.danbro.gmall.api.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.danbro.gmall.api.po.PmsBaseAttrValuePo;
import lombok.Data;

/**
 * @author Danrbo
 * @date 2019/10/18 14:09
 * description
 **/
@Data
@TableName(value = "pms_base_attr_value")
public class PmsBaseAttrValueDto extends PmsBaseAttrValuePo {
}
