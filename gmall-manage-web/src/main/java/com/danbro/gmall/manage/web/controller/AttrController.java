package com.danbro.gmall.manage.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.dto.*;
import com.danbro.gmall.api.po.*;
import com.danbro.gmall.api.service.AttrService;
import com.danbro.gmall.api.vo.PmsBaseAttrInfoVo;
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
    public List<PmsBaseAttrInfoDto> getAttrInfo(@RequestParam("catalog3Id") Long id) {
        return attrService.getAttrInfoByCatalog3Id(id);
    }

    @PostMapping("/attrInfo")
    public String addAttr(@RequestBody PmsBaseAttrInfoVo pmsBaseAttrInfoVo) {
        String flag = attrService.addOrUpdateAttr(pmsBaseAttrInfoVo);
        return "success";
    }

    @GetMapping("/attrValueList")
    public List<PmsBaseAttrValueDto> getAttrValue(@RequestParam("attrId") Long id) {
        return attrService.getAttrValueByAttrInfoId(id);
    }

    @GetMapping("/baseSaleAttrList")
    public List<PmsBaseSaleAttrDto> getSaleAttrList() {
        return attrService.getSaleAttrList();
    }

    @GetMapping("spuSaleAttrList")
    public List<PmsProductSaleAttrDto> getSkuSaleAttrValueList(@RequestParam("spuId") Long id){
        return attrService.getProductSaleAttrListBySpuId(id);
    }

    @GetMapping("spuImageList")
    public List<PmsProductImageDto> getProductImageList(@RequestParam("spuId") Long id){
        return attrService.getProductImageListBySpuId(id);
    }

}
