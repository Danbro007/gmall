package com.danbro.gmall.manage.service.Iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.bean.PmsBaseAttrInfo;
import com.danbro.gmall.api.bean.PmsBaseAttrValue;
import com.danbro.gmall.api.service.AttrService;
import com.danbro.gmall.manage.service.mapper.PmsBaseAttrInfoMapper;
import com.danbro.gmall.manage.service.mapper.PmsBaseAttrValueMapper;
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
    private PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    private PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    public AttrServiceImpl(PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper, PmsBaseAttrValueMapper pmsBaseAttrValueMapper) {
        this.pmsBaseAttrInfoMapper = pmsBaseAttrInfoMapper;
        this.pmsBaseAttrValueMapper = pmsBaseAttrValueMapper;
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueByAttrInfoId(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("attr_id", id);
        return pmsBaseAttrValueMapper.selectByMap(columnMap);
    }


    @Override
    public List<PmsBaseAttrInfo> getAttrInfoByCatalog3Id(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("catalog3_id", id);
        return pmsBaseAttrInfoMapper.selectByMap(columnMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addOrUpdateAttr(PmsBaseAttrInfo pmsBaseAttrInfo) {
        Long id = pmsBaseAttrInfo.getId();
        List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
        if (id != null) {
            pmsBaseAttrInfoMapper.updateById(pmsBaseAttrInfo);
            HashMap<String, Object> columnMap = new HashMap<>(16);
            columnMap.put("attr_id",pmsBaseAttrInfo.getId());
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
