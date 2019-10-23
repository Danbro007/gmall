package com.danbro.gmall.search.utils;

import com.danbro.gmall.api.dto.PmsBaseAttrInfoDto;
import com.danbro.gmall.api.dto.PmsBaseAttrValueDto;
import com.danbro.gmall.api.dto.PmsSearchSkuInfoDto;
import com.danbro.gmall.api.dto.PmsSkuAttrValueDto;
import com.danbro.gmall.api.vo.PmsSearchParamCrumbVo;
import com.danbro.gmall.api.vo.PmsSearchParamVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/18 15:43
 * description
 **/
public class SearchControllerUtils {

    @Autowired


    /**
     * 获得面包屑列表
     * @param delValueIdList 已经点击的属性值列表
     * @param pmsBaseAttrInfoDtoList 属性列表
     * @param pmsSearchParamVo  搜索参数
     * @return  面包屑列表
     */
    public static List<PmsSearchParamCrumbVo> getPmsSearchParamCrumbVoList(String[] delValueIdList, List<PmsBaseAttrInfoDto> pmsBaseAttrInfoDtoList, PmsSearchParamVo pmsSearchParamVo){
        ArrayList<PmsSearchParamCrumbVo> pmsSearchParamCrumbVoList = new ArrayList<>();
        for (String delValueId : delValueIdList) {
            Iterator<PmsBaseAttrInfoDto> iterator = pmsBaseAttrInfoDtoList.iterator();
            while (iterator.hasNext()) {
                PmsBaseAttrInfoDto pmsBaseAttrInfoDto = iterator.next();
                for (PmsBaseAttrValueDto pmsBaseAttrValueDto : pmsBaseAttrInfoDto.getAttrValueList()) {
                    if (delValueId.equals(pmsBaseAttrValueDto.getId().toString())) {
                        PmsSearchParamCrumbVo pmsSearchParamCrumbVo = new PmsSearchParamCrumbVo();
                        pmsSearchParamCrumbVo.setValueId(Long.parseLong(delValueId));
                        pmsSearchParamCrumbVo.setUrlParam(getUrlParam(pmsSearchParamVo, delValueId));
                        pmsSearchParamCrumbVo.setValueName(pmsBaseAttrValueDto.getValueName());
                        pmsSearchParamCrumbVoList.add(pmsSearchParamCrumbVo);
                        iterator.remove();
                    }
                }
            }
        }
        return pmsSearchParamCrumbVoList;
    }

    /**
     * 通过参数拼接url地址
     *
     * @param pmsSearchParamVo 参数对象
     * @return url地址
     */
    public static String getUrlParam(PmsSearchParamVo pmsSearchParamVo, String... delValueId) {
        String keyword = pmsSearchParamVo.getKeyword();
        Long catalog3Id = pmsSearchParamVo.getCatalog3Id();
        String[] skuAttrValueList = pmsSearchParamVo.getValueId();
        String urlParam = "";
        /*关键字*/
        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam += "&";
            }
            urlParam += "keyword=" + keyword;
        }
        /*三级菜单*/
        if (catalog3Id != null) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam += "&";
            }
            urlParam += "catalog3Id=" + catalog3Id;
        }
        /*属性*/
        if (skuAttrValueList != null) {
            for (String pmsSkuAttrValue : skuAttrValueList) {
                if (delValueId.length == 0 || !delValueId[0].equals(pmsSkuAttrValue)) {
                    urlParam += "&valueId=" + pmsSkuAttrValue;
                }
            }
        }
        return urlParam;
    }

    /**
     * 通过商品列表获得去重后的属性和属性值集合
     *
     * @param skuInfoList 商品列表
     * @return 去重后的属性和属性值集合
     */
    public static HashSet<Long> getValueIdSet(List<PmsSearchSkuInfoDto> skuInfoList) {
        HashSet<Long> valueIdSet = new HashSet<>();
        for (PmsSearchSkuInfoDto pmsSearchSkuInfoDto : skuInfoList) {
            for (PmsSkuAttrValueDto pmsSkuAttrValueDto : pmsSearchSkuInfoDto.getSkuAttrValueList()) {
                valueIdSet.add(pmsSkuAttrValueDto.getValueId());
            }
        }
        return valueIdSet;
    }
}
