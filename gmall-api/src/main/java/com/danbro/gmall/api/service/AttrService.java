package com.danbro.gmall.api.service;

import com.danbro.gmall.api.dto.*;
import com.danbro.gmall.api.vo.PmsBaseAttrInfoVo;

import java.util.HashSet;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 20:51
 * description
 **/
public interface AttrService {
    /**
     * 通过目录id返回其相关的属性
     * @param id 目录id
     * @return 目录先关的属性
     */
    List<PmsBaseAttrInfoDto> getAttrInfoByCatalog3Id(Long id);

    /**
     * 添加或者更新属性平台信息
     * @param pmsBaseAttrInfoVo 属性平台对象
     * @return 成功信息
     */
    String addOrUpdateAttr(PmsBaseAttrInfoVo pmsBaseAttrInfoVo);

    /**
     * 通过属性平台的id找到所有属性值
     * @param id 属性平台id
     * @return 所有属性值
     */
    List<PmsBaseAttrValueDto> getAttrValueByAttrInfoId(Long id);

    /**
     * 获得所有的属性列表
     * @return 属性列表
     */
    List<PmsBaseSaleAttrDto> getSaleAttrList();

    /**
     * 通过spuId获得所有相关的属性
     * @param id spuId
     * @return 相关的属性列表
     */
    List<PmsProductSaleAttrDto> getProductSaleAttrListBySpuId(Long id);

    /**
     * 通过spuId查找相关的产品图片
     * @param id spuId
     * @return 产品图片
     */
    List<PmsProductImageDto> getProductImageListBySpuId(Long id);


    /**
     * 通过属性值集合找到相应的属性
     * @param valueIdSet 属性值集合
     * @return  属性
     */
    List<PmsBaseAttrInfoDto> getAttrValueByValueId(HashSet<Long> valueIdSet);
}
