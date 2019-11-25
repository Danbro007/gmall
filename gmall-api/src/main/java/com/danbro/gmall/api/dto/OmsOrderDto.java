package com.danbro.gmall.api.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.danbro.gmall.api.po.OmsOrderItemPo;
import com.danbro.gmall.api.po.OmsOrderPo;
import lombok.Data;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/11/25 13:28
 * description
 **/
@Data
@TableName(value = "oms_order")
public class OmsOrderDto extends OmsOrderPo {

    @TableField(exist = false)
    private List<OmsOrderItemPo> omsOrderItemPoList;

}
