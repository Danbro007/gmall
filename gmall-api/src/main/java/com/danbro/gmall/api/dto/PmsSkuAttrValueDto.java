package com.danbro.gmall.api.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.danbro.gmall.api.po.PmsSkuAttrValuePo;
import lombok.Data;

/**
 * @author Danrbo
 * @date 2019/10/18 13:36
 * description
 **/
@Data
@TableName(value = "pms_sku_attr_value")
public class PmsSkuAttrValueDto  extends PmsSkuAttrValuePo {

}
