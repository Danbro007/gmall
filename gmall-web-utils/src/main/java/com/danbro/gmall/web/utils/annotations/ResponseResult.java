package com.danbro.gmall.web.utils.annotations;

import java.lang.annotation.*;

/**
 * @author Danrbo
 * @date 2019/11/11 10:59
 * description 返回结果注解
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseResult {

}
