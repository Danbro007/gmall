package com.danbro.gmall.common.utils.processor;

import com.danbro.gmall.common.utils.dataBinder.CustomServletRequestDataBinder;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.servlet.ServletRequest;

/**
 * @Classname CustomServletModelAttributeMethodProcessor
 * @Description TODO 自定义属性处理器
 * @Date 2019/11/29 15:33
 * @Author Danrbo
 */
public class CustomServletModelAttributeMethodProcessor extends ServletModelAttributeMethodProcessor {
    /**
     * Class constructor.
     *
     * @param annotationNotRequired if "true", non-simple method arguments and
     *                              return values are considered model attributes with or without a
     *                              {@code @ModelAttribute} annotation
     */
    public CustomServletModelAttributeMethodProcessor(boolean annotationNotRequired) {
        super(annotationNotRequired);
    }

    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
        Assert.state(servletRequest != null,"No servletRequest");
        ServletRequestDataBinder servletRequestDataBinder = (ServletRequestDataBinder) binder;
        //更换请求参数绑定器
        new CustomServletRequestDataBinder(servletRequestDataBinder.getTarget()).bind(servletRequest);

    }
}
