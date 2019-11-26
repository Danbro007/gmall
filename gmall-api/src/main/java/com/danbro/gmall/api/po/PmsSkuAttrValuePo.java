package com.danbro.gmall.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Danrbo
 * @date 2019/9/10 11:01
 * description
 **/
@Data
public class PmsSkuAttrValuePo implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long attrId;

    private Long valueId;

    private Long skuId;


}
