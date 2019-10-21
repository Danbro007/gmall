package com.danbro.gmall.manage.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.danbro.gmall.api.dto.PmsBaseAttrInfoDto;
import com.danbro.gmall.api.po.PmsBaseAttrInfoPo;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 14:29
 * description
 **/
public interface PmsBaseAttrInfoMapper extends BaseMapper<PmsBaseAttrInfoDto> {
    /**
     * 通过valueId列表找到相应的属性
     * @param valueIdStr value列表字符串
     * @return 属性和属性值
     */
    List<PmsBaseAttrInfoDto> getAttrValueByValueId(String valueIdStr);

}
