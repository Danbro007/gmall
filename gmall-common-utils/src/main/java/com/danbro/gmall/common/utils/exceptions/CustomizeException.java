package com.danbro.gmall.common.utils.exceptions;


import lombok.Getter;

/**
 * @author Danrbo
 * @date 2019/11/19 14:27
 * description
 **/
@Getter
public class CustomizeException extends RuntimeException {

    private String message;
    private Integer code;

    public CustomizeException(MyCustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

}
