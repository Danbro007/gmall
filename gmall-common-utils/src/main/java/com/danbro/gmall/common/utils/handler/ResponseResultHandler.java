package com.danbro.gmall.common.utils.handler;



import com.danbro.gmall.common.utils.annotations.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Danrbo
 * @date 2019/11/11 11:17
 * description
 **/
@Slf4j
//@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {
    private static final String RESPONSE_RESULT_ANN = "RESPONSE_RESULT_ANN";

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        ResponseResult attribute = (ResponseResult) request.getAttribute(RESPONSE_RESULT_ANN);
        return attribute == null ? false : true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("进入返回体 重写格式");
//        if (body instanceof CustomizeErrorCode){
//            CustomizeErrorCode customizeCode = (CustomizeErrorCode) body;
//            return ResultDto.failureOf(customizeCode);
//        }
//        return ResultDto.success(body);
//    }
        return null;
    }
}
