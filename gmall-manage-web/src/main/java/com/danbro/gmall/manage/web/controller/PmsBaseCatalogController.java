package com.danbro.gmall.manage.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.po.PmsBaseCatalog1Po;
import com.danbro.gmall.api.po.PmsBaseCatalog2Po;
import com.danbro.gmall.api.po.PmsBaseCatalog3Po;
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
    public List<PmsBaseCatalog1Po> getAllCatalog1() {
        return pmsBaseCatalogService.getAllCatalog1();
    }

    @GetMapping("/catalog2")
    public List<PmsBaseCatalog2Po> getAllCatalog2(@RequestParam("catalog1Id") Long id) {
        return pmsBaseCatalogService.getAllCatalog2(id);
    }

    @GetMapping("/catalog3")
    public List<PmsBaseCatalog3Po> getAllCatalog3(@RequestParam("catalog2Id") Long id) {
        return pmsBaseCatalogService.getAllCatalog3(id);
    }

}
