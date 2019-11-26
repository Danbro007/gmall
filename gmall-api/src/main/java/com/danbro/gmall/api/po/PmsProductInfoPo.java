package com.danbro.gmall.api.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Danrbo
 * @date 2019/9/11 13:51
 * description
 **/
@Data
public class PmsProductInfoPo implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String productName;
    private String description;
    private String catalog3Id;
    private Long tmId;

}

