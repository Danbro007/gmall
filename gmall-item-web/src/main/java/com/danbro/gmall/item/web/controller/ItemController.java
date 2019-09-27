package com.danbro.gmall.item.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.bean.PmsProductSaleAttr;
import com.danbro.gmall.api.bean.PmsSkuInfo;
import com.danbro.gmall.api.service.PmsProductService;
import com.danbro.gmall.api.service.PmsSkuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/18 16:24
 * description
 **/
@Controller
@CrossOrigin
public class ItemController {

    @Reference
    PmsSkuService pmsSkuService;

    @Reference
    PmsProductService pmsProductService;

    @RequestMapping("{skuId}.html")
    public String itemView(@PathVariable("skuId") Long skuId, Model model){
        PmsSkuInfo pmsSkuInfo = pmsSkuService.getSkuById(skuId);
        String skuInfoMap = pmsSkuService.selectSkuSaleAttrListCheckBySpu(pmsSkuInfo.getProductId());
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductService.selectSpuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(), skuId);
        model.addAttribute("skuInfo",pmsSkuInfo);
        model.addAttribute("spuSaleAttrValueList",pmsProductSaleAttrs);
        model.addAttribute("valuesSku",skuInfoMap);
        return "item";
    }

}
