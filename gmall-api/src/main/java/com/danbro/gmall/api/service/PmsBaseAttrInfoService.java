package com.danbro.gmall.api.service;

import com.danbro.gmall.api.bean.PmsBaseAttrInfo;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 14:22
 * description
 **/
public interface PmsBaseAttrInfoService {
    List<PmsBaseAttrInfo> getAttrInfoByCatalog3Id(Long id);
}
