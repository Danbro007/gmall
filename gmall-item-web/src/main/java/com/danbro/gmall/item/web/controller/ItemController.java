package com.danbro.gmall.item.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.dto.PmsProductSaleAttrDto;
import com.danbro.gmall.api.dto.PmsSkuInfoDto;
import com.danbro.gmall.api.service.PmsProductService;
import com.danbro.gmall.api.service.PmsSkuService;
import com.danbro.gmall.web.utils.annotations.LoginRequired;
import com.danbro.gmall.web.utils.annotations.ResponseResult;
import com.danbro.gmall.web.utils.bean.Result;
import com.danbro.gmall.web.utils.enums.ResultCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        PmsSkuInfoDto pmsSkuInfoDto = pmsSkuService.getSkuById(skuId);
        String skuInfoMap = pmsSkuService.selectSkuSaleAttrListCheckBySpu(pmsSkuInfoDto.getProductId());
        List<PmsProductSaleAttrDto> pmsProductSaleAttrDtoList = pmsProductService.selectSpuSaleAttrListCheckBySku(pmsSkuInfoDto.getProductId(), skuId);
        model.addAttribute("skuInfo", pmsSkuInfoDto);
        model.addAttribute("spuSaleAttrValueList", pmsProductSaleAttrDtoList);
        model.addAttribute("valuesSku",skuInfoMap);
        return "item";
    }

}
