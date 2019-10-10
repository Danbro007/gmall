package com.danbro.gmall.api.service;

import com.danbro.gmall.api.bean.PmsSearchParam;
import com.danbro.gmall.api.bean.PmsSearchSkuInfo;

import java.io.IOException;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/8 16:17
 * description
 **/
public interface SearchService {


    /**
     * 通过筛选条件找到过滤后的sku
     * @param pmsSearchParam 筛选条件
     * @return 过滤后的sku列表
     */
    List<PmsSearchSkuInfo> getSkuInfoListByParam(PmsSearchParam pmsSearchParam) throws IOException;

}
