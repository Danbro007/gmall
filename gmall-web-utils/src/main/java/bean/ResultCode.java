package bean;

import lombok.Data;

/**
 * @author Danrbo
 * @date 2019/10/21 14:40
 * description
 **/

public enum ResultCode implements MyCustomResultCode{
    /*
    * 传输数据成功
    * */
    SUCCESS(200,"成功"),

    /*
    * 按搜索条件未找到相关商品
    */
    ITEM_NOT_FOUND(400,"商品未找到"),
    /*
    *
    * */
    KEYWORD_NOT_FOUND(401,"关键词未找到");

    private Integer code;
    private String message;

    @Override

    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }


    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
