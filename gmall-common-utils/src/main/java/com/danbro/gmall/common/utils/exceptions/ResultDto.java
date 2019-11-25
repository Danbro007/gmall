package com.danbro.gmall.common.utils.exceptions;

import lombok.Data;

import java.util.Map;

/**
 * @author Danrbo
 * @date 2019/11/11 10:41
 * description 封装返回结果
 **/
@Data
public class ResultDto {

    private String message;
    private Integer code;
    private Object data;
    private Map errorMap;


    //返回成功

    public static ResultDto successOf(CustomizeErrorCode customizeErrorCode) {
        ResultDto result = new ResultDto();
        result.setCode(customizeErrorCode.getCode());
        result.setMessage(customizeErrorCode.getMessage());
        return result;
    }

    public static ResultDto success(CustomizeErrorCode customizeErrorCode, Object data) {
        ResultDto result = new ResultDto();
        result.setData(data);
        result.setCode(customizeErrorCode.getCode());
        result.setMessage(customizeErrorCode.getMessage());
        return result;
    }

    //失败

    /**
     * @param customizeCode 错误代码和信息
     * @return 结果实体类
     */
    public static ResultDto errorOf(CustomizeErrorCode customizeCode) {
        ResultDto result = new ResultDto();
        result.setMessage(customizeCode.getMessage());
        result.setCode(customizeCode.getCode());
        return result;
    }

    public static ResultDto errorOf(CustomizeErrorCode customizeCode, Map errorMap) {
        ResultDto result = new ResultDto();
        result.setData(customizeCode.getCode());
        result.setMessage(customizeCode.getMessage());
        result.setErrorMap(errorMap);
        return result;
    }

    public static ResultDto errorOf(Integer code, String message) {
        ResultDto resultDto = new ResultDto();
        resultDto.setMessage(message);
        resultDto.setCode(code);
        return resultDto;
    }

    public static ResultDto errorOf(CustomizeException customizeException) {
        return ResultDto.errorOf(customizeException.getCode(),customizeException.getMessage());
    }

}
