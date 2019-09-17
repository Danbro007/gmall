package com.danbro.gmall.api.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Danrbo
 * @date 2019/9/11 16:20
 * description
 **/
@Data
@TableName(value = "pms_product_image")
public class PmsProductImage implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Long productId;
    private String imgName;
    private String imgUrl;
}
