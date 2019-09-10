package com.danbro.gmall.api.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 11:01
 * description
 **/
@Data
@TableName(value = "pms_base_catalog3")
public class PmsBaseCatalog3 implements Serializable{
    @TableId
    private Long id;
    private String name;
    private Long catalog2Id;

}
