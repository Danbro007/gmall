package com.danbro.gmall.item.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.dto.PmsProductSaleAttrDto;
import com.danbro.gmall.api.dto.PmsSkuInfoDto;
import com.danbro.gmall.api.service.PmsProductService;
import com.danbro.gmall.api.service.SkuService;


import com.danbro.gmall.common.utils.exceptions.CustomizeErrorCode;
import com.danbro.gmall.common.utils.exceptions.CustomizeException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    SkuService skuService;

    @Reference
    PmsProductService pmsProductService;


    @GetMapping("{skuId}.html")
    public String itemView(@PathVariable("skuId") Long skuId, Model model) {
        PmsSkuInfoDto pmsSkuInfoDto = skuService.getSkuById(skuId);
        if (pmsSkuInfoDto == null){
            throw new CustomizeException(CustomizeErrorCode.ITEM_NOT_FOUND);
        }
        String skuInfoMap = skuService.selectSkuSaleAttrListCheckBySpu(pmsSkuInfoDto.getProductId());
        List<PmsProductSaleAttrDto> pmsProductSaleAttrDtoList = pmsProductService.selectSpuSaleAttrListCheckBySku(pmsSkuInfoDto.getProductId(), skuId);
        model.addAttribute("skuInfo", pmsSkuInfoDto);
        model.addAttribute("spuSaleAttrValueList", pmsProductSaleAttrDtoList);
        model.addAttribute("valuesSku",skuInfoMap);
        return "item";
    }


}
