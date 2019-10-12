package com.danbro.gmall.api.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/8 16:15
 * description sku搜索筛选
 **/
@Data
public class PmsSearchParam  implements Serializable {

    private String keyword;
    private Long catalog3Id;
    private String[] valueId;

}
