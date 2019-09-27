package com.danbro.gmall.manage.service.Iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.bean.*;
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
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    private PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

    public SpuServiceImpl(SpuInfoMapper spuInfoMapper, PmsProductImageMapper pmsProductImageMapper, PmsProductSaleAttrMapper pmsProductSaleAttrMapper, PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper) {
        this.spuInfoMapper = spuInfoMapper;
        this.pmsProductImageMapper = pmsProductImageMapper;
        this.pmsProductSaleAttrMapper = pmsProductSaleAttrMapper;
        this.pmsProductSaleAttrValueMapper = pmsProductSaleAttrValueMapper;
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
            for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductInfo.getSaleAttrList()) {
                pmsProductSaleAttr.setProductId(pmsProductInfo.getId());
                pmsProductSaleAttrMapper.insert(pmsProductSaleAttr);
                for (PmsProductSaleAttrValue pmsProductSaleAttrValue : pmsProductSaleAttr.getSaleAttrValueList()) {
                    pmsProductSaleAttrValue.setProductId(pmsProductInfo.getId());
                    pmsProductSaleAttrValueMapper.insert(pmsProductSaleAttrValue);
                }
            }
        }

        return insert;
    }
    @Override
    public List<PmsProductSaleAttr> selectSpuSaleAttrListCheckBySku(Long productId,Long skuId){
        return pmsProductSaleAttrMapper.selectSpuSaleAttrListCheckBySku(productId,skuId);
    }

}
