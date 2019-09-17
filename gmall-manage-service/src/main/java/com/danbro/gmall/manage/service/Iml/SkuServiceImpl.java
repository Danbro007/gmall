package com.danbro.gmall.manage.service.Iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.bean.PmsSkuAttrValue;
import com.danbro.gmall.api.bean.PmsSkuImage;
import com.danbro.gmall.api.bean.PmsSkuInfo;
import com.danbro.gmall.api.bean.PmsSkuSaleAttrValue;
import com.danbro.gmall.api.service.PmsSkuService;
import com.danbro.gmall.manage.service.mapper.PmsSkuAttrValueMapper;
import com.danbro.gmall.manage.service.mapper.PmsSkuImageMapper;
import com.danbro.gmall.manage.service.mapper.PmsSkuInfoMapper;
import com.danbro.gmall.manage.service.mapper.PmsSkuSaleAttrValueMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Danrbo
 * @date 2019/9/17 18:55
 * description
 **/
@Service
public class SkuServiceImpl implements PmsSkuService {

    private PmsSkuInfoMapper pmsSkuInfoMapper;
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    private PmsSkuImageMapper pmsSkuImageMapper;
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;


    public SkuServiceImpl(PmsSkuInfoMapper pmsSkuInfoMapper, PmsSkuAttrValueMapper pmsSkuAttrValueMapper, PmsSkuImageMapper pmsSkuImageMapper, PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper) {
        this.pmsSkuInfoMapper = pmsSkuInfoMapper;
        this.pmsSkuAttrValueMapper = pmsSkuAttrValueMapper;
        this.pmsSkuImageMapper = pmsSkuImageMapper;
        this.pmsSkuSaleAttrValueMapper = pmsSkuSaleAttrValueMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSkuInfo(PmsSkuInfo pmsSkuInfo) {
        int flag = pmsSkuInfoMapper.insert(pmsSkuInfo);
        if (flag == 1){
            for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuInfo.getSkuAttrValueList()) {
                pmsSkuAttrValue.setSkuId(pmsSkuInfo.getId());
                pmsSkuAttrValueMapper.insert(pmsSkuAttrValue);

            }
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuInfo.getSkuSaleAttrValueList()) {
                pmsSkuSaleAttrValue.setSkuId(pmsSkuInfo.getId());
                pmsSkuSaleAttrValueMapper.insert(pmsSkuSaleAttrValue);
            }
            for (PmsSkuImage pmsSkuImage : pmsSkuInfo.getSkuImageList()) {
                pmsSkuImage.setSkuId(pmsSkuInfo.getId());
                pmsSkuImageMapper.insert(pmsSkuImage);
            }
        }
        return flag;
    }
}
