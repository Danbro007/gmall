package com.danbro.gmall.manage.service.Iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.danbro.gmall.api.bean.PmsBaseAttrValue;
import com.danbro.gmall.api.service.PmsBaseAttrValueService;
import com.danbro.gmall.manage.service.mapper.PmsBaseAttrValueMapper;

import java.util.HashMap;
import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 14:35
 * description
 **/
@Service
public class PmsBaseAttrValueImpl implements PmsBaseAttrValueService {

    private PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    public PmsBaseAttrValueImpl(PmsBaseAttrValueMapper pmsBaseAttrValueMapper) {
        this.pmsBaseAttrValueMapper = pmsBaseAttrValueMapper;
    }


    @Override
    public List<PmsBaseAttrValue> getAttrValueByAttrId(Long id) {
        HashMap<String, Object> columnMap = new HashMap<>(16);
        columnMap.put("attr_id",id);
        return pmsBaseAttrValueMapper.selectByMap(columnMap);
    }
}
