package com.danbro.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.bean.PmsSearchParam;
import com.danbro.gmall.api.bean.PmsSearchSkuInfo;
import com.danbro.gmall.api.service.SearchService;
import javafx.scene.control.Skin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/10/8 15:25
 * description
 **/

@CrossOrigin
@Controller
public class SearchController {

    @Reference
    SearchService searchService;

    @GetMapping("/list.html")
    public String search(PmsSearchParam pmsSearchParam , ModelMap modelMap){
        try {
            List<PmsSearchSkuInfo> skuInfoList = searchService.getSkuInfoListByParam(pmsSearchParam);
            modelMap.put("skuLsInfoList",skuInfoList);
        } catch (IOException e) {
        }
        return "list";
    }

    @GetMapping("/index")
    public String indexView(){
        return "index";
    }

}
