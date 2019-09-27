package com.danbro.gmall.exception.handle.Exception;

/**
 * @author Danrbo
 * @date 2019/9/24 16:27
 * description
 **/


public class CustomException extends RuntimeException{

    private Integer errorCode;
    private String errorMessage;

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


    public CustomException(MyCustomizeErrorCode error){
        this.errorCode = error.getErrorCode();
        this.errorMessage = error.getErrorMessage();
    }


}
