package com.danbro.gmall.manage.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.bean.*;
import com.danbro.gmall.api.service.AttrService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 14:41
 * description
 **/
@RestController
@CrossOrigin
public class AttrController {
    @Reference
    AttrService attrService;

    @GetMapping("/attrInfoList")
    public List<PmsBaseAttrInfo> getAttrInfo(@RequestParam("catalog3Id") Long id) {
        return attrService.getAttrInfoByCatalog3Id(id);
    }

    @PostMapping("/attrInfo")
    public String addAttr(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo) {
        String flag = attrService.addOrUpdateAttr(pmsBaseAttrInfo);
        return "success";
    }

    @GetMapping("/attrValueList")
    public List<PmsBaseAttrValue> getAttrValue(@RequestParam("attrId") Long id) {
        return attrService.getAttrValueByAttrInfoId(id);
    }

    @GetMapping("/baseSaleAttrList")
    public List<PmsBaseSaleAttr> getSaleAttrList() {
        return attrService.getSaleAttrList();
    }

    @GetMapping("spuSaleAttrList")
    public List<PmsProductSaleAttr> getSkuSaleAttrValueList(@RequestParam("spuId") Long id){
        return attrService.getProductSaleAttrListBySpuId(id);
    }

    @GetMapping("spuImageList")
    public List<PmsProductImage> getProductImageList(@RequestParam("spuId") Long id){
        return attrService.getProductImageListBySpuId(id);
    }

}
