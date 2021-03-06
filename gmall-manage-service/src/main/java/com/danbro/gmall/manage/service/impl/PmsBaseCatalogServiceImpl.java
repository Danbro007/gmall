package com.danbro.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.po.PmsBaseCatalog1Po;
import com.danbro.gmall.api.po.PmsBaseCatalog2Po;
import com.danbro.gmall.api.po.PmsBaseCatalog3Po;
import com.danbro.gmall.api.service.PmsBaseCatalogService;
import com.danbro.gmall.manage.service.mapper.PmsBaseCatalog1Mapper;
import com.danbro.gmall.manage.service.mapper.PmsBaseCatalog2Mapper;
import com.danbro.gmall.manage.service.mapper.PmsBaseCatalog3Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 11:21
 * description
 **/
@Service
public class PmsBaseCatalogServiceImpl implements PmsBaseCatalogService {
    private PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;
    private PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;
    private PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    public PmsBaseCatalogServiceImpl(PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper, PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper, PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper) {
        this.pmsBaseCatalog1Mapper = pmsBaseCatalog1Mapper;
        this.pmsBaseCatalog2Mapper = pmsBaseCatalog2Mapper;
        this.pmsBaseCatalog3Mapper = pmsBaseCatalog3Mapper;
    }

    @Override
    public List<PmsBaseCatalog1Po> getAllCatalog1() {
        return pmsBaseCatalog1Mapper.selectList(null);
    }

    @Override
    public List<PmsBaseCatalog2Po> getAllCatalog2(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("catalog1_id", id);
        return pmsBaseCatalog2Mapper.selectByMap(columnMap);
    }

    @Override
    public List<PmsBaseCatalog3Po> getAllCatalog3(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("catalog2_id", id);
        return pmsBaseCatalog3Mapper.selectByMap(columnMap);
    }
}
