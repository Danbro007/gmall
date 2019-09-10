package com.danbro.gmall.manage.service.Iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.bean.PmsBaseAttrInfo;
import com.danbro.gmall.api.service.PmsBaseAttrInfoService;
import com.danbro.gmall.manage.service.mapper.PmsBaseAttrInfoMapper;

import java.util.HashMap;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 14:27
 * description
 **/
@Service
public class PmsBaseAttrInfoImpl implements PmsBaseAttrInfoService {
    private PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;

    public PmsBaseAttrInfoImpl(PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper) {
        this.pmsBaseAttrInfoMapper = pmsBaseAttrInfoMapper;
    }

    @Override
    public List<PmsBaseAttrInfo> getAttrInfoByCatalog3Id(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("catalog3_id", id);
        return pmsBaseAttrInfoMapper.selectByMap(columnMap);
    }
}
