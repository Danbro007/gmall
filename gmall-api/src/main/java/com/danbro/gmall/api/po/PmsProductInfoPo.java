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

