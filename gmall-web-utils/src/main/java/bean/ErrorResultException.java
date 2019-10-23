package bean;

import lombok.Data;

/**
 * @author Danrbo
 * @date 2019/10/21 16:07
 * description
 **/
@Data
public class ErrorResultException extends RuntimeException {


    private String message;
    private Integer code;


    public ErrorResultException(ResultCode resultCode){
        this.message = resultCode.getMessage();
        this.code = resultCode.getCode();
    }

}
