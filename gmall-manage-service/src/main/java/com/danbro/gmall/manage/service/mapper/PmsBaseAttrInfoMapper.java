package com.danbro.gmall.manage.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.danbro.gmall.api.bean.PmsBaseAttrInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 14:29
 * description
 **/
public interface PmsBaseAttrInfoMapper extends BaseMapper<PmsBaseAttrInfo> {
    /**
     * 通过valueId列表找到相应的属性
     * @param valueIdStr value列表字符串
     * @return 属性和属性值
     */
    List<PmsBaseAttrInfo> getAttrValueByValueId(String valueIdStr);

}
