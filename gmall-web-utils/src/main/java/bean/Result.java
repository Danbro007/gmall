package bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Danrbo
 * @date 2019/10/21 14:51
 * description
 **/

@Data
public class Result implements Serializable {

    private String message;
    private Integer code;
    private Object data;
    private ResultCode resultCode;

//    public Result(ResultCode resultCode, Object data) {
//        this.data = data;
//        this.code = resultCode.code();
//        this.message = resultCode.message();
//    }
//
//    public Result() {
//    }


    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }


    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public static Result failure(ResultCode resultCode,Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    public static Result failure(ErrorResultException errorResultException) {
        Result result = new Result();
        result.setCode(errorResultException.getCode());
        result.setMessage(errorResultException.getMessage());
        return result;
    }
}
