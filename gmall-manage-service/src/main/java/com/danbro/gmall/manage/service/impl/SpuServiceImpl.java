package com.danbro.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.dto.PmsProductImageDto;
import com.danbro.gmall.api.dto.PmsProductInfoDto;
import com.danbro.gmall.api.dto.PmsProductSaleAttrDto;
import com.danbro.gmall.api.dto.PmsProductSaleAttrValueDto;
import com.danbro.gmall.api.service.PmsProductService;
import com.danbro.gmall.api.vo.PmsProductInfoVo;
import com.danbro.gmall.manage.service.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/11 13:58
 * description
 **/
@Service
public class SpuServiceImpl implements PmsProductService {
    private SpuInfoMapper spuInfoMapper;
    private PmsProductImageMapper pmsProductImageMapper;
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    private PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

    public SpuServiceImpl(SpuInfoMapper spuInfoMapper, PmsProductImageMapper pmsProductImageMapper, PmsProductSaleAttrMapper pmsProductSaleAttrMapper, PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper) {
        this.spuInfoMapper = spuInfoMapper;
        this.pmsProductImageMapper = pmsProductImageMapper;
        this.pmsProductSaleAttrMapper = pmsProductSaleAttrMapper;
        this.pmsProductSaleAttrValueMapper = pmsProductSaleAttrValueMapper;
    }

    @Override
    public List<PmsProductInfoDto> getProductInfoListByCatalogId(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("catalog3_id",id);
        return spuInfoMapper.selectByMap(columnMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addProductInfo(PmsProductInfoVo pmsProductInfoVo) {
        PmsProductInfoDto pmsProductInfoDto = new PmsProductInfoDto();
        BeanUtils.copyProperties(pmsProductInfoVo,pmsProductInfoDto);
        int insert = spuInfoMapper.insert(pmsProductInfoDto);
        if (insert == 1){
            for (PmsProductImageDto pmsProductImageDto : pmsProductInfoDto.getImageList()) {
                pmsProductImageDto.setProductId(pmsProductInfoDto.getId());
                pmsProductImageMapper.insert(pmsProductImageDto);
            }
            for (PmsProductSaleAttrDto pmsProductSaleAttrDto : pmsProductInfoDto.getSaleAttrList()) {
                pmsProductSaleAttrDto.setProductId(pmsProductInfoDto.getId());
                pmsProductSaleAttrMapper.insert(pmsProductSaleAttrDto);
                for (PmsProductSaleAttrValueDto pmsProductSaleAttrValueDto : pmsProductSaleAttrDto.getSaleAttrValueList()) {
                    pmsProductSaleAttrValueDto.setProductId(pmsProductInfoDto.getId());
                    pmsProductSaleAttrValueMapper.insert(pmsProductSaleAttrValueDto);
                }
            }
        }

        return insert;
    }
    @Override
    public List<PmsProductSaleAttrDto> selectSpuSaleAttrListCheckBySku(Long productId, Long skuId){
        return pmsProductSaleAttrMapper.selectSpuSaleAttrListCheckBySku(productId,skuId);
    }

}
