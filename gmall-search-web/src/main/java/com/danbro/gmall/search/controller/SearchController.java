package com.danbro.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.bean.*;
import com.danbro.gmall.api.service.AttrService;
import com.danbro.gmall.api.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/8 15:25
 * description
 **/

@CrossOrigin
@Controller
public class SearchController {

    @Reference
    SearchService searchService;

    @Reference
    AttrService attrService;

    @GetMapping("/list.html")
    public String search(PmsSearchParam pmsSearchParam, ModelMap modelMap) {
        try {
            List<PmsSearchSkuInfo> skuInfoList = searchService.getSkuInfoListByParam(pmsSearchParam);
            List<PmsBaseAttrInfo> pmsBaseAttrInfoList = attrService.getAttrValueByValueId(getValueIdSet(skuInfoList));
            String[] delValueIdList = pmsSearchParam.getValueId();
            if (delValueIdList != null) {
                modelMap.put("attrValueSelectedList", getPmsSearchParamCrumbs(delValueIdList,pmsBaseAttrInfoList,pmsSearchParam));
            }
            modelMap.put("skuLsInfoList", skuInfoList);
            modelMap.put("urlParam", getUrlParam(pmsSearchParam));
            modelMap.put("attrList", pmsBaseAttrInfoList);
        } catch (IOException e) {
        }
        return "list";
    }

    @GetMapping("/index")
    public String indexView() {
        return "index";
    }

    /**
     * 通过参数拼接url地址
     *
     * @param pmsSearchParam 参数对象
     * @return url地址
     */
    private String getUrlParam(PmsSearchParam pmsSearchParam, String... delValueId) {
        String keyword = pmsSearchParam.getKeyword();
        Long catalog3Id = pmsSearchParam.getCatalog3Id();
        String[] skuAttrValueList = pmsSearchParam.getValueId();
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
    private HashSet<Long> getValueIdSet(List<PmsSearchSkuInfo> skuInfoList) {
        HashSet<Long> valueIdSet = new HashSet<>();
        for (PmsSearchSkuInfo pmsSearchSkuInfo : skuInfoList) {
            for (PmsSkuAttrValue pmsSkuAttrValue : pmsSearchSkuInfo.getSkuAttrValueList()) {
                valueIdSet.add(pmsSkuAttrValue.getValueId());
            }
        }
        return valueIdSet;
    }

    /**
     * 获得面包屑列表
     * @param delValueIdList 已经点击的属性值列表
     * @param pmsBaseAttrInfoList 属性列表
     * @param pmsSearchParam  搜索参数
     * @return  面包屑列表
     */
    private List<PmsSearchParamCrumb> getPmsSearchParamCrumbs(String[] delValueIdList,List<PmsBaseAttrInfo> pmsBaseAttrInfoList,PmsSearchParam pmsSearchParam){
        ArrayList<PmsSearchParamCrumb> pmsSearchParamCrumbs = new ArrayList<>();
        for (String delValueId : delValueIdList) {
            Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfoList.iterator();
            while (iterator.hasNext()) {
                PmsBaseAttrInfo pmsBaseAttrInfo = iterator.next();
                for (PmsBaseAttrValue pmsBaseAttrValue : pmsBaseAttrInfo.getAttrValueList()) {
                    if (delValueId.equals(pmsBaseAttrValue.getId().toString())) {
                        PmsSearchParamCrumb pmsSearchParamCrumb = new PmsSearchParamCrumb();
                        pmsSearchParamCrumb.setValueId(Long.parseLong(delValueId));
                        pmsSearchParamCrumb.setUrlParam(getUrlParam(pmsSearchParam, delValueId));
                        pmsSearchParamCrumb.setValueName(pmsBaseAttrValue.getValueName());
                        pmsSearchParamCrumbs.add(pmsSearchParamCrumb);
                        iterator.remove();
                    }
                }
            }
        }
        return pmsSearchParamCrumbs;
    }

}
