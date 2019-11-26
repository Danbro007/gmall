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
public class PmsSkuImagePo implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long skuId;

    private String imgName;

    private String imgUrl;

    private Long productImgId;

    private String isDefault;

}