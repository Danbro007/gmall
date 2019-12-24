package com.danbro.gmall.common.utils.exceptions;

/**
 * @author Danrbo
 * @date 2019/11/19 16:07
 * description
 **/
public enum  CustomizeErrorCode implements MyCustomizeErrorCode {
    /**
     * 代码
     */
    SUCCESS(200,"成功"),

    /**
     *
      */
    ITEM_NOT_FOUND(400,"商品不存在！"),
    CART_UPDATE_FAIL(401,"购物车商品更新失败"),
    LOGIN_FAIL(402,"登录失败"),
    PAYMENT_NOT_FOUND(403,"未查询到相关支付信息"),
    /**
     * 服务器错误
     */
    SERVER_ERROR(500,"服务器出错");


    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
