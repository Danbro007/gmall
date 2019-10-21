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
 * @date 2019/9/17 12:44
 * description
 **/
@Data
public class PmsProductSaleAttrPo implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long productId;

    private Long saleAttrId;

    private String saleAttrName;

}
