package com.danbro.gmall.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Danrbo
 * @date 2019/10/11 14:57
 * description
 **/
@Data
public class PmsSearchParamCrumbVo implements Serializable {

    private String valueName;
    private Long valueId;
    private String urlParam;

}
