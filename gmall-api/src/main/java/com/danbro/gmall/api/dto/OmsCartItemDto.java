package com.danbro.gmall.api.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.danbro.gmall.api.po.OmsCartItemPo;
import lombok.Data;

/**
 * @author Danrbo
 * @date 2019/10/28 14:56
 * description 购物车里的单个商品信息
 **/
@TableName(value = "oms_cart_item")
@Data
public class OmsCartItemDto extends OmsCartItemPo {
}
