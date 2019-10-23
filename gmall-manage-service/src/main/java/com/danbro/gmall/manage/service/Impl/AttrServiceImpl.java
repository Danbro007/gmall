package com.danbro.gmall.manage.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.dto.*;
import com.danbro.gmall.api.service.AttrService;
import com.danbro.gmall.api.vo.PmsBaseAttrInfoVo;
import com.danbro.gmall.manage.service.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
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
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    public AttrServiceImpl(PmsProductSaleAttrMapper pmsProductSaleAttrMapper, PmsProductImageMapper pmsProductImageMapper, PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper, PmsBaseAttrValueMapper pmsBaseAttrValueMapper, PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper, PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper, PmsSkuAttrValueMapper pmsSkuAttrValueMapper) {
        this.pmsProductSaleAttrMapper = pmsProductSaleAttrMapper;
        this.pmsProductImageMapper = pmsProductImageMapper;
        this.pmsBaseAttrInfoMapper = pmsBaseAttrInfoMapper;
        this.pmsBaseAttrValueMapper = pmsBaseAttrValueMapper;
        this.pmsBaseSaleAttrMapper = pmsBaseSaleAttrMapper;
        this.pmsProductSaleAttrValueMapper = pmsProductSaleAttrValueMapper;
        this.pmsSkuAttrValueMapper = pmsSkuAttrValueMapper;
    }

    @Override
    public List<PmsBaseAttrValueDto> getAttrValueByAttrInfoId(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("attr_id", id);
        return pmsBaseAttrValueMapper.selectByMap(columnMap);
    }

    @Override
    public List<PmsBaseSaleAttrDto> getSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectList(null);
    }

    @Override
    public List<PmsProductSaleAttrDto> getProductSaleAttrListBySpuId(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("product_id", id);
        List<PmsProductSaleAttrDto> pmsProductSaleAttrDtoList = pmsProductSaleAttrMapper.selectByMap(columnMap);
        for (PmsProductSaleAttrDto pmsProductSaleAttrDto : pmsProductSaleAttrDtoList) {
            HashMap<String, Object> searchMap = new HashMap<>(16);
            searchMap.put("product_id", pmsProductSaleAttrDto.getProductId());
            searchMap.put("sale_attr_id", pmsProductSaleAttrDto.getSaleAttrId());
            List<PmsProductSaleAttrValueDto> pmsProductSaleAttrValueDtoList = pmsProductSaleAttrValueMapper.selectByMap(searchMap);
            pmsProductSaleAttrDto.setSaleAttrValueList(pmsProductSaleAttrValueDtoList);
        }
        return pmsProductSaleAttrDtoList;
    }

    @Override
    public List<PmsProductImageDto> getProductImageListBySpuId(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("product_id", id);
        return pmsProductImageMapper.selectByMap(columnMap);
    }

    @Override
    public List<PmsBaseAttrInfoDto> getAttrValueByValueId(HashSet<Long> valueIdSet) {
        String valueIdSetStr = StringUtils.join(valueIdSet,",");
        return pmsBaseAttrInfoMapper.getAttrValueByValueId(valueIdSetStr);
    }


    @Override
    public List<PmsBaseAttrInfoDto> getAttrInfoByCatalog3Id(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("catalog3_id", id);
        List<PmsBaseAttrInfoDto> pmsBaseAttrInfoDtoList = pmsBaseAttrInfoMapper.selectByMap(columnMap);
        for (PmsBaseAttrInfoDto pmsBaseAttrInfoDto : pmsBaseAttrInfoDtoList) {
            HashMap<String, Object> searchMap = new HashMap<>(16);
            searchMap.put("attr_id", pmsBaseAttrInfoDto.getId());
            List<PmsBaseAttrValueDto> pmsBaseAttrValueDtoList = pmsBaseAttrValueMapper.selectByMap(searchMap);
            pmsBaseAttrInfoDto.setAttrValueList(pmsBaseAttrValueDtoList);
        }
        return pmsBaseAttrInfoDtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addOrUpdateAttr(PmsBaseAttrInfoVo pmsBaseAttrInfoVo) {
        PmsBaseAttrInfoDto pmsBaseAttrInfoDto = new PmsBaseAttrInfoDto();
        BeanUtils.copyProperties(pmsBaseAttrInfoVo,pmsBaseAttrInfoDto);
        Long attrId = pmsBaseAttrInfoDto.getId();
        List<PmsBaseAttrValueDto> attrValueList = pmsBaseAttrInfoDto.getAttrValueList();
        /*修改*/
        if (attrId != null) {
            pmsBaseAttrInfoMapper.updateById(pmsBaseAttrInfoDto);
            HashMap<String, Object> columnMap = new HashMap<>(16);
            columnMap.put("attr_id", pmsBaseAttrInfoDto.getId());
            pmsBaseAttrValueMapper.deleteByMap(columnMap);
            for (PmsBaseAttrValueDto pmsBaseAttrValueDto : attrValueList) {
                pmsBaseAttrValueDto.setAttrId(pmsBaseAttrInfoDto.getId());
                pmsBaseAttrValueMapper.insert(pmsBaseAttrValueDto);
            }
        } else {
            /*添加*/
            pmsBaseAttrInfoMapper.insert(pmsBaseAttrInfoDto);
            for (PmsBaseAttrValueDto pmsBaseAttrValueDto : attrValueList) {
                pmsBaseAttrValueDto.setAttrId(pmsBaseAttrInfoDto.getId());
                pmsBaseAttrValueMapper.insert(pmsBaseAttrValueDto);
            }
        }
        return "success";
    }


}
