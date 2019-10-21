package com.danbro.gmall.api.service;

import com.danbro.gmall.api.dto.PmsSearchParamDto;
import com.danbro.gmall.api.dto.PmsSearchSkuInfoDto;
import com.danbro.gmall.api.vo.PmsSearchParamVo;
import com.danbro.gmall.api.vo.PmsSearchSkuInfoVo;

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
     * @param pmsSearchParamVo sku筛选参数Vo
     * @return 过滤后的sku列表
     */
    List<PmsSearchSkuInfoDto> getSkuInfoListByParam(PmsSearchParamVo pmsSearchParamVo) throws IOException;

}
