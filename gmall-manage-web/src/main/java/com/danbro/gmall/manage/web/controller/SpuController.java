package com.danbro.gmall.manage.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.bean.PmsProductInfo;
import com.danbro.gmall.api.service.PmsProductService;
import com.danbro.gmall.manage.web.utils.PmsUploadUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/11 13:53
 * description
 **/
@CrossOrigin
@RestController
public class SpuController {

    @Reference
    PmsProductService pmsProductService;

    @GetMapping("/spuList")
    public List<PmsProductInfo> getProductInfoListByCatalogId(@RequestParam("catalog3Id") Long id){
        return pmsProductService.getProductInfoListByCatalogId(id);
    }

    @PostMapping("/spuInfo")
    public String addSpu(@RequestBody PmsProductInfo pmsProductInfo){
        int flag =  pmsProductService.addProductInfo(pmsProductInfo);
        if (flag == 1){
            return "success";
        }
        return "fail";
    }

    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam("file")MultipartFile multipartFile){
        return PmsUploadUtil.uploadImage(multipartFile);
    }
}
