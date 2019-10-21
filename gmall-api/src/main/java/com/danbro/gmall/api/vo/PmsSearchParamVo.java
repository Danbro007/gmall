package com.danbro.gmall.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Danrbo
 * @date 2019/10/8 16:15
 * description sku搜索参数Vo
 **/
@Data
public class PmsSearchParamVo implements Serializable {

    private String keyword;
    private Long catalog3Id;
    private String[] valueId;

}
