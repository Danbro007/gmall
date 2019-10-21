package com.danbro.gmall.api.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.danbro.gmall.api.po.PmsProductImagePo;
import lombok.Data;

/**
 * @author Danrbo
 * @date 2019/10/18 14:18
 * description
 **/
@Data
@TableName(value = "pms_product_image")
public class PmsProductImageDto extends PmsProductImagePo {
}
