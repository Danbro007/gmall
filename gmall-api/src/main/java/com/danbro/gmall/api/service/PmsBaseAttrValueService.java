package com.danbro.gmall.api.service;

import com.danbro.gmall.api.bean.PmsBaseAttrValue;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 14:22
 * description
 **/
public interface PmsBaseAttrValueService {
    List<PmsBaseAttrValue> getAttrValueByAttrId(Long id);
}
