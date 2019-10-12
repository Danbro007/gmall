package com.danbro.gmall.manage.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.bean.InsertSkuInfo;
import com.danbro.gmall.api.bean.PmsSkuInfo;
import com.danbro.gmall.api.service.PmsSkuService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Danrbo
 * @date 2019/9/17 14:38
 * description
 **/
@RestController
@CrossOrigin
public class SkuController {

    @Reference
    PmsSkuService pmsSkuService;


    @PostMapping("/skuInfo")
    public String addSkuInfo(@RequestBody InsertSkuInfo insertSkuInfo){
        int flag = pmsSkuService.addSkuInfo(insertSkuInfo);
        if (flag == 1){
            return "success";
        }return "fail";
    }

}
