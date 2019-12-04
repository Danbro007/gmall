package com.danbro.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.danbro.gmall.api.dto.*;
import com.danbro.gmall.api.service.AttrService;
import com.danbro.gmall.api.vo.PmsBaseAttrInfoVo;
import com.danbro.gmall.manage.service.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

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

    public AttrServiceImpl(PmsProductSaleAttrMapper pmsProductSaleAttrMapper, PmsProductImageMapper pmsProductImageMapper, PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper, PmsBaseAttrValueMapper pmsBaseAttrValueMapper, PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper, PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper) {
        this.pmsProductSaleAttrMapper = pmsProductSaleAttrMapper;
        this.pmsProductImageMapper = pmsProductImageMapper;
        this.pmsBaseAttrInfoMapper = pmsBaseAttrInfoMapper;
        this.pmsBaseAttrValueMapper = pmsBaseAttrValueMapper;
        this.pmsBaseSaleAttrMapper = pmsBaseSaleAttrMapper;
        this.pmsProductSaleAttrValueMapper = pmsProductSaleAttrValueMapper;
    }

    @Override
    public List<PmsBaseAttrValueDto> getAttrValueByAttrInfoId(Long id) {
        QueryWrapper<PmsBaseAttrValueDto> pmsBaseAttrValueDtoQueryWrapper = new QueryWrapper<>();
        pmsBaseAttrValueDtoQueryWrapper.eq("attr_id",id);
        return pmsBaseAttrValueMapper.selectList(pmsBaseAttrValueDtoQueryWrapper);
    }

    @Override
    public List<PmsBaseSaleAttrDto> getSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectList(null);
    }

    @Override
    public List<PmsProductSaleAttrDto> getProductSaleAttrListBySpuId(Long id) {
        QueryWrapper<PmsProductSaleAttrDto> saleAttrQueryWrapper = new QueryWrapper<>();
        saleAttrQueryWrapper.eq("product_id",id);
        List<PmsProductSaleAttrDto> saleAttrDtoList = pmsProductSaleAttrMapper.selectList(saleAttrQueryWrapper);
        for (PmsProductSaleAttrDto pmsProductSaleAttrDto : saleAttrDtoList) {
            QueryWrapper<PmsProductSaleAttrValueDto> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("product_id",pmsProductSaleAttrDto.getProductId()).eq("sale_attr_id",pmsProductSaleAttrDto.getSaleAttrId());
            List<PmsProductSaleAttrValueDto> pmsProductSaleAttrValueDtoList = pmsProductSaleAttrValueMapper.selectList(queryWrapper);
            pmsProductSaleAttrDto.setSaleAttrValueList(pmsProductSaleAttrValueDtoList);
        }
        return saleAttrDtoList;
    }

    @Override
    public List<PmsProductImageDto> getProductImageListBySpuId(Long id) {
        QueryWrapper<PmsProductImageDto> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id",id);
        return pmsProductImageMapper.selectList(queryWrapper);
    }

    @Override
    public List<PmsBaseAttrInfoDto> getAttrValueByValueId(HashSet<Long> valueIdSet) {
        String valueIdStr = StringUtils.join(valueIdSet, ",");
        return pmsBaseAttrInfoMapper.getAttrValueByValueId(valueIdStr);
    }


    @Override
    public List<PmsBaseAttrInfoDto> getAttrInfoByCatalog3Id(Long id) {
        QueryWrapper<PmsBaseAttrInfoDto> attrInfoQueryWrapper = new QueryWrapper<>();
        attrInfoQueryWrapper.eq("catalog3_id",id);
        List<PmsBaseAttrInfoDto> pmsBaseAttrInfoDtoList = pmsBaseAttrInfoMapper.selectList(attrInfoQueryWrapper);
        for (PmsBaseAttrInfoDto pmsBaseAttrInfoDto : pmsBaseAttrInfoDtoList) {
            QueryWrapper<PmsBaseAttrValueDto> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("attr_id",pmsBaseAttrInfoDto.getId());
            List<PmsBaseAttrValueDto> pmsBaseAttrValueDtoList = pmsBaseAttrValueMapper.selectList(queryWrapper);
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
        //获得这个属性的所有平台属性值
        List<PmsBaseAttrValueDto> attrValueList = pmsBaseAttrInfoDto.getAttrValueList();
        /*修改*/
        if (attrId != null) {
            pmsBaseAttrInfoMapper.updateById(pmsBaseAttrInfoDto);
            QueryWrapper<PmsBaseAttrValueDto> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("attr_id",pmsBaseAttrInfoDto.getId());
            pmsBaseAttrValueMapper.delete(queryWrapper);
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
