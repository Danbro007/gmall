package com.danbro.gmall.api.service;

import com.danbro.gmall.api.bean.PmsBaseCatalog1;
import com.danbro.gmall.api.bean.PmsBaseCatalog2;
import com.danbro.gmall.api.bean.PmsBaseCatalog3;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/9/10 11:15
 * description
 **/
public interface PmsBaseCatalogService {
    /**获得所有一级目录列表
     * @return 获得所有一级目录的列表
     */
    List<PmsBaseCatalog1> getAllCatalog1();

    /**
     * 通过一级目录id查找到二级目录列表
     *
     * @param id 一级目录id
     * @return 二级目录的列表
     */
    List<PmsBaseCatalog2> getAllCatalog2(Long id);

    /**
     * 通过二级目录id查找到三级目录列表
     *
     * @param id 二级级目录id
     * @return 三级目录的列表
     */
    List<PmsBaseCatalog3> getAllCatalog3(Long id);
}
