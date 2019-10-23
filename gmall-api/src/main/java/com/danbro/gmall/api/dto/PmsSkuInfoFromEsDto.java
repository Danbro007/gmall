package com.danbro.gmall.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/8 11:05
 * description 从elasticsearch搜索到的sku结果
 **/
@Data
public class PmsSkuInfoFromEsDto implements Serializable {

    private Long id;
    private String skuName;
    private String skuDesc;
    private Long catalog3Id;
    private Double price;
    private String skuDefaultImg;
    private Long productId;
    private List<PmsSkuAttrValueDto> skuAttrValueList;

}
