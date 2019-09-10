package com.danbro.gmall.api.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Danrbo
 * @date 2019/9/10 10:58
 * description
 **/
@TableName(value = "pms_base_catalog1")
@Data
public class PmsBaseCatalog1 implements Serializable {
    @TableId
    private Long id;
    private String name;
}
