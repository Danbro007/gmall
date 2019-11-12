package com.danbro.gmall.web.utils.enums;

/**
 * @author Danrbo
 * @date 2019/11/11 10:31
 * description
 **/
public enum ResultCode {

    //状态码
    SUCCESS(200,"成功"),
    FAIL(400,"失败"),
    ITEM_NOT_FOUND(401,"商品未找到");
    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
    public Integer getCode(){
        return code;
    }

}
