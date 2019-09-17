package com.danbro.gmall.manage.service.Iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.bean.PmsBaseSaleAttr;
import com.danbro.gmall.api.bean.PmsBaseSaleAttrValue;
import com.danbro.gmall.api.bean.PmsProductImage;
import com.danbro.gmall.api.bean.PmsProductInfo;
import com.danbro.gmall.api.service.PmsProductService;
import com.danbro.gmall.manage.service.mapper.*;
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
    private PmsBaseSaleAttrValueMapper pmsBaseSaleAttrValueMapper;

    public SpuServiceImpl(SpuInfoMapper spuInfoMapper, PmsProductImageMapper pmsProductImageMapper, PmsBaseSaleAttrValueMapper pmsBaseSaleAttrValueMapper) {
        this.spuInfoMapper = spuInfoMapper;
        this.pmsProductImageMapper = pmsProductImageMapper;
        this.pmsBaseSaleAttrValueMapper = pmsBaseSaleAttrValueMapper;
    }

    @Override
    public List<PmsProductInfo> getProductInfoListByCatalogId(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("catalog3_id",id);
        return spuInfoMapper.selectByMap(columnMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addProductInfo(PmsProductInfo pmsProductInfo) {
        int insert = spuInfoMapper.insert(pmsProductInfo);
        if (insert == 1){
            for (PmsProductImage pmsProductImage : pmsProductInfo.getImageList()) {
                pmsProductImage.setProductId(pmsProductInfo.getId());
                pmsProductImageMapper.insert(pmsProductImage);
            }
            for (PmsBaseSaleAttr pmsBaseSaleAttr : pmsProductInfo.getSaleAttrList()) {
                for (PmsBaseSaleAttrValue pmsBaseSaleAttrValue : pmsBaseSaleAttr.getSaleAttrValueList()) {
                    pmsBaseSaleAttrValue.setProductId(pmsProductInfo.getId());
                    pmsBaseSaleAttrValueMapper.insert(pmsBaseSaleAttrValue);
                }
            }
        }

        return insert;
    }
}
