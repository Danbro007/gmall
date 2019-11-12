package com.danbro.gmall.web.utils.bean;

import com.danbro.gmall.web.utils.enums.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Danrbo
 * @date 2019/11/11 10:41
 * description 封装返回结果
 **/
@Data
public class Result implements Serializable {

    private ResultCode resultCode;
    private Object data;

    public Result(ResultCode resultCode, Object data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    public Result() {
    }

    //返回成功

    public static Result success(){
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static Result success(Object data){
        Result result = new Result();
        result.setData(data);
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    //失败

    /**
     *
     * @param resultCode 结果代码和信息
     * @return 结果实体类
     */
    public static Result failure(ResultCode resultCode){
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public static Result failure(ResultCode resultCode, Object data){
        Result result = new Result();
        result.setData(data);
        result.setResultCode(resultCode);
        return result;
    }

}
