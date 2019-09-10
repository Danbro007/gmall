package com.danbro.gmall.manage.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.bean.PmsBaseCatalog1;
import com.danbro.gmall.api.bean.PmsBaseCatalog2;
import com.danbro.gmall.api.bean.PmsBaseCatalog3;
import com.danbro.gmall.api.service.PmsBaseCatalogService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 11:12
 * description
 **/


@RestController
@CrossOrigin
public class PmsBaseCatalogController {
    @Reference
    PmsBaseCatalogService pmsBaseCatalogService;

    @PostMapping("/getCatalog1")
    public List<PmsBaseCatalog1> getAllCatalog1(){
        return pmsBaseCatalogService.getAllCatalog1();
    }

    @PostMapping("/getCatalog2")
    public List<PmsBaseCatalog2> getAllCatalog2(@RequestParam("catalog1Id") Long id){
        return pmsBaseCatalogService.getAllCatalog2(id);
    }

    @PostMapping("/getCatalog3")
    public List<PmsBaseCatalog3> getAllCatalog3(@RequestParam("catalog2Id") Long id){
        List<PmsBaseCatalog3> allCatalog3 = pmsBaseCatalogService.getAllCatalog3(id);
        for (PmsBaseCatalog3 pmsBaseCatalog3 : allCatalog3) {
            System.out.println(pmsBaseCatalog3);
        }
        return pmsBaseCatalogService.getAllCatalog3(id);
    }



}