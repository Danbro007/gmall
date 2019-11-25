package com.danbro.gmall.common.utils.exceptions;

/**
 * @author Danrbo
 * @date 2019/11/19 14:21
 * description
 **/
public interface MyCustomizeErrorCode {
    /**
     * 获得状态码
     * @return 状态码
     */
    Integer getCode();

    /**
     * 获得状态消息
     * @return 获得状态消息
     */
    String getMessage();

}
