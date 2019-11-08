package com.danbro.gmall.web.utils.interceptor;

import com.danbro.gmall.common.utils.util.CookieUtil;
import com.danbro.gmall.common.utils.util.HttpClientUtil;
import com.danbro.gmall.web.utils.annotations.LoginRequired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Danrbo
 * @date 2019/11/6 15:21
 * description 用户认证拦截器
 **/

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequired loginRequired = handlerMethod.getMethodAnnotation(LoginRequired.class);
        //判断有没有@LoginRequiredzhu注解
        if (loginRequired == null) {
            return true;
        }
        boolean successNecessary = loginRequired.successNecessary();
        String token = "";
        //旧token
        String oldToken = CookieUtil.getCookieValue(request, "oldToken", true);
        //新token
        String newToken = CookieUtil.getCookieValue(request, "newToken", true);
        if (!StringUtils.isBlank(oldToken)) {
            token = oldToken;
        }
        if (!StringUtils.isBlank(newToken)) {
            token = newToken;
        }
        //想认证中心获取用户token是否合法
        String success = HttpClientUtil.doGet("http://passport.gmall.com:8085/identify?token=" + token);
        //判断是否必须要验证成功
        String flag = "success";
        if (successNecessary) {
            //验证失败 跳回登录页面
            if (!flag.equals(success)) {
                response.sendRedirect("http://passport.gmall.com:8085/index?returnUrl=" + request.getRequestURL());
                return false;
            }
            request.setAttribute("memberId","1");
            request.setAttribute("nickName","shanqijie");
        }
        else {
            if (!flag.equals(success)){
                request.setAttribute("memberId","1");
                request.setAttribute("nickName","shanqijie");
            }
        }
        return true;
    }
}
