package com.danbro.gmall.api.dto;

import com.danbro.gmall.api.po.PmsSkuAttrValuePo;
import com.danbro.gmall.api.vo.PmsSearchSkuInfoVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/17 14:37
 * description
 **/
@Data
public class PmsSearchSkuInfoDto implements Serializable {
    private Long id;
    private String skuName;
    private String skuDesc;
    private Long catalog3Id;
    private Double price;
    private String skuDefaultImg;
    private Long productId;
    private List<PmsSkuAttrValueDto> skuAttrValueList;
}
