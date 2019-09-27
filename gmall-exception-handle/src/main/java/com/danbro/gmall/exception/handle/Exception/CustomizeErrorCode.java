package com.danbro.gmall.exception.handle.Exception;

/**
 * @author Danrbo
 * @date 2019/9/24 16:22
 * description
 **/
public enum  CustomizeErrorCode implements MyCustomizeErrorCode {
    /**
     * 要查找的sku不存在
     */
    SKU_NOT_FOUND(400,"此sku不存在"),
    /**
     * 要查找的spu不存在
     */
    SPU_NOT_FOUND(401,"此spu不存在");

    private Integer errorCode;
    private String errorMessage;

    CustomizeErrorCode(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
