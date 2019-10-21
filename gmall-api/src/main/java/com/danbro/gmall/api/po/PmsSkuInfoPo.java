package com.danbro.gmall.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 11:01
 * description
 **/
@Data
public class PmsSkuInfoPo implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long productId;

    private Double price;

    private String skuName;

    private Double weight;

    private String skuDesc;

    private Long catalog3Id;

    private String skuDefaultImg;

}
