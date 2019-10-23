package annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @author Danrbo
 * @date 2019/10/21 15:11
 * description
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE,METHOD})
@Documented
public @interface ResponseResult {
}
