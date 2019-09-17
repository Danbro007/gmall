package com.danbro.gmall.api.service;

import com.danbro.gmall.api.bean.*;

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
    List<PmsBaseAttrInfo> getAttrInfoByCatalog3Id(Long id);

    /**
     * 添加或者更新属性平台信息
     * @param pmsBaseAttrInfo 属性平台对象
     * @return 成功信息
     */
    String addOrUpdateAttr(PmsBaseAttrInfo pmsBaseAttrInfo);

    /**
     * 通过属性平台的id找到所有属性值
     * @param id 属性平台id
     * @return 所有属性值
     */
    List<PmsBaseAttrValue> getAttrValueByAttrInfoId(Long id);

    /**
     * 获得所有的属性列表
     * @return 属性列表
     */
    List<PmsBaseSaleAttr> getSaleAttrList();

    /**
     * 通过spuId获得所有相关的属性
     * @param id spuId
     * @return 相关的属性列表
     */
    List<PmsProductSaleAttr> getProductSaleAttrListBySpuId(Long id);

    /**
     * 通过spuId查找相关的产品图片
     * @param id spuId
     * @return 产品图片
     */
    List<PmsProductImage> getProductImageListBySpuId(Long id);

}
