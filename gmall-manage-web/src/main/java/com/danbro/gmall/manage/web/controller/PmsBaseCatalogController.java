package com.danbro.gmall.manage.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.bean.PmsBaseCatalog1;
import com.danbro.gmall.api.bean.PmsBaseCatalog2;
import com.danbro.gmall.api.bean.PmsBaseCatalog3;
import com.danbro.gmall.api.service.PmsBaseCatalogService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/catalog1")
    public List<PmsBaseCatalog1> getAllCatalog1() {
        return pmsBaseCatalogService.getAllCatalog1();
    }

    @GetMapping("/catalog2")
    public List<PmsBaseCatalog2> getAllCatalog2(@RequestParam("catalog1Id") Long id) {
        return pmsBaseCatalogService.getAllCatalog2(id);
    }

    @GetMapping("/catalog3")
    public List<PmsBaseCatalog3> getAllCatalog3(@RequestParam("catalog2Id") Long id) {
        return pmsBaseCatalogService.getAllCatalog3(id);
    }

}
