package com.danbro.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.danbro.gmall.api.dto.PmsBaseAttrInfoDto;
import com.danbro.gmall.api.dto.PmsSearchSkuInfoDto;
import com.danbro.gmall.api.service.AttrService;
import com.danbro.gmall.api.service.SearchService;
import com.danbro.gmall.api.vo.PmsSearchParamVo;
import com.danbro.gmall.search.utils.SearchControllerUtils;
import com.danbro.gmall.web.utils.annotations.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
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

    @Reference
    AttrService attrService;

    @GetMapping("/list.html")
    public String search(PmsSearchParamVo pmsSearchParamVo, ModelMap modelMap) {
        try {
            List<PmsSearchSkuInfoDto> skuInfoList = searchService.getSkuInfoListByParam(pmsSearchParamVo);
            List<PmsBaseAttrInfoDto> pmsBaseAttrInfoDtoList = attrService.getAttrValueByValueId(SearchControllerUtils.getValueIdSet(skuInfoList));
            String[] delValueIdList = pmsSearchParamVo.getValueId();
            if (delValueIdList != null) {
                modelMap.put("attrValueSelectedList", SearchControllerUtils.getPmsSearchParamCrumbVoList(delValueIdList, pmsBaseAttrInfoDtoList, pmsSearchParamVo));
            }
            modelMap.put("skuLsInfoList", skuInfoList);
            modelMap.put("urlParam", SearchControllerUtils.getUrlParam(pmsSearchParamVo));
            modelMap.put("attrList", pmsBaseAttrInfoDtoList);
        } catch (IOException e) {
        }
        return "list";
    }

    @GetMapping("/index")
    @LoginRequired
    public String indexView(HttpServletRequest request, Model model) {
        String memberId = (String)request.getAttribute("memberId");
        String nickname = (String)request.getAttribute("nickname");
        if (nickname != null && memberId != null){
            model.addAttribute("nickname",nickname);
        }
        return "index";
    }





}
