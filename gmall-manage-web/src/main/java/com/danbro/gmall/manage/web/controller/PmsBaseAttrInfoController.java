package com.danbro.gmall.manage.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.bean.PmsBaseAttrInfo;
import com.danbro.gmall.api.service.PmsBaseAttrInfoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 14:41
 * description
 **/
@RestController
@CrossOrigin
public class PmsBaseAttrInfoController {
    @Reference
    PmsBaseAttrInfoService pmsBaseAttrInfoService;

    @GetMapping("/attrInfoList")
    public List<PmsBaseAttrInfo> getAttrInfo(@RequestParam("catalog3Id") Long id){
        return pmsBaseAttrInfoService.getAttrInfoByCatalog3Id(id);
    }
}
