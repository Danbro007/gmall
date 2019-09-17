package com.danbro.gmall.api.service;

import com.danbro.gmall.api.bean.PmsSkuInfo;

/**
 * @author Danrbo
 * @date 2019/9/17 18:54
 * description
 **/
public interface PmsSkuService {


    /**
     * 添加sku信息
     * @param pmsSkuInfo sku信息
     * @return 成功信息
     */
    int addSkuInfo(PmsSkuInfo pmsSkuInfo);

}
