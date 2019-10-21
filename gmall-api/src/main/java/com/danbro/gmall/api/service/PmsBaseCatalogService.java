package com.danbro.gmall.api.service;

import com.danbro.gmall.api.po.PmsBaseCatalog1Po;
import com.danbro.gmall.api.po.PmsBaseCatalog2Po;
import com.danbro.gmall.api.po.PmsBaseCatalog3Po;

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
    List<PmsBaseCatalog1Po> getAllCatalog1();

    /**
     * 通过一级目录id查找到二级目录列表
     *
     * @param id 一级目录id
     * @return 二级目录的列表
     */
    List<PmsBaseCatalog2Po> getAllCatalog2(Long id);

    /**
     * 通过二级目录id查找到三级目录列表
     *
     * @param id 二级级目录id
     * @return 三级目录的列表
     */
    List<PmsBaseCatalog3Po> getAllCatalog3(Long id);
}
