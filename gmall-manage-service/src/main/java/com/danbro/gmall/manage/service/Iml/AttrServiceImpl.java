package com.danbro.gmall.manage.service.Iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.bean.*;
import com.danbro.gmall.api.service.AttrService;
import com.danbro.gmall.manage.service.mapper.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 20:53
 * description
 **/
@Service
public class AttrServiceImpl implements AttrService {
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    private PmsProductImageMapper pmsProductImageMapper;
    private PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    private PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
    private PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;
    private PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

    public AttrServiceImpl(PmsProductSaleAttrMapper pmsProductSaleAttrMapper, PmsProductImageMapper pmsProductImageMapper, PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper, PmsBaseAttrValueMapper pmsBaseAttrValueMapper, PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper, PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper) {
        this.pmsProductSaleAttrMapper = pmsProductSaleAttrMapper;
        this.pmsProductImageMapper = pmsProductImageMapper;
        this.pmsBaseAttrInfoMapper = pmsBaseAttrInfoMapper;
        this.pmsBaseAttrValueMapper = pmsBaseAttrValueMapper;
        this.pmsBaseSaleAttrMapper = pmsBaseSaleAttrMapper;
        this.pmsProductSaleAttrValueMapper = pmsProductSaleAttrValueMapper;
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueByAttrInfoId(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("attr_id", id);
        return pmsBaseAttrValueMapper.selectByMap(columnMap);
    }

    @Override
    public List<PmsBaseSaleAttr> getSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectList(null);
    }

    @Override
    public List<PmsProductSaleAttr> getProductSaleAttrListBySpuId(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("product_id", id);
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.selectByMap(columnMap);
        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductSaleAttrs) {
            HashMap<String, Object> searchMap = new HashMap<>(16);
            searchMap.put("product_id",pmsProductSaleAttr.getProductId());
            searchMap.put("sale_attr_id",pmsProductSaleAttr.getSaleAttrId());
            List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.selectByMap(searchMap);
            pmsProductSaleAttr.setSaleAttrValueList(pmsProductSaleAttrValues);
        }
        return pmsProductSaleAttrs;
    }

    @Override
    public List<PmsProductImage> getProductImageListBySpuId(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("product_id", id);
        return pmsProductImageMapper.selectByMap(columnMap);
    }


    @Override
    public List<PmsBaseAttrInfo> getAttrInfoByCatalog3Id(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("catalog3_id", id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.selectByMap(columnMap);
        for (PmsBaseAttrInfo pmsBaseAttrInfo : pmsBaseAttrInfos) {
            HashMap<String, Object> searchMap = new HashMap<>(16);
            searchMap.put("attr_id", pmsBaseAttrInfo.getId());
            List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.selectByMap(searchMap);
            pmsBaseAttrInfo.setAttrValueList(pmsBaseAttrValues);
        }
        return pmsBaseAttrInfos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addOrUpdateAttr(PmsBaseAttrInfo pmsBaseAttrInfo) {
        Long id = pmsBaseAttrInfo.getId();
        List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
        if (id != null) {
            pmsBaseAttrInfoMapper.updateById(pmsBaseAttrInfo);
            HashMap<String, Object> columnMap = new HashMap<>(16);
            columnMap.put("attr_id", pmsBaseAttrInfo.getId());
            pmsBaseAttrValueMapper.deleteByMap(columnMap);
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                pmsBaseAttrValueMapper.insert(pmsBaseAttrValue);
            }
        } else {
            pmsBaseAttrInfoMapper.insert(pmsBaseAttrInfo);
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                pmsBaseAttrValueMapper.insert(pmsBaseAttrValue);
            }
        }
        return "success";
    }


}
