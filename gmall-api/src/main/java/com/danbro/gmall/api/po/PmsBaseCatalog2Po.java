package com.danbro.gmall.api.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Danrbo
 * @date 2019/9/10 10:59
 * description
 **/
@Data
@TableName(value = "pms_base_catalog2")
public class PmsBaseCatalog2Po implements Serializable {
    @TableId
    private Long id;
    private String name;
    private Long catalog1Id;
}
