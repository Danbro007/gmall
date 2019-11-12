package com.danbro.gmall.web.utils.interceptor;

import com.alibaba.fastjson.JSON;
import com.danbro.gmall.common.utils.util.CookieUtil;
import com.danbro.gmall.common.utils.util.HttpClientUtil;
import com.danbro.gmall.web.utils.annotations.LoginRequired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author Danrbo
 * @date 2019/11/6 15:21
 * description 用户认证拦截器
 **/

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            LoginRequired loginRequired = handlerMethod.getMethodAnnotation(LoginRequired.class);
            //判断有没有@LoginRequiredzhu注解
            if (loginRequired == null) {
                return true;
            }
            boolean successNecessary = loginRequired.successNecessary();
            String token = "";
            //从Cookie获取旧token
            String oldToken = CookieUtil.getCookieValue(request, "oldToken", true);
            if (!StringUtils.isBlank(oldToken)) {
                token = oldToken;
            }
            //新token
            String newToken = request.getParameter("token");
            if (!StringUtils.isBlank(newToken)) {
                token = newToken;

            }
            HashMap<String, Object> map = new HashMap<>(16);
            String status = "success";
            //获得token里的用户信息
            if (StringUtils.isNotBlank(token)) {
                String userJson = HttpClientUtil.doGet("http://passport.gmall.com:8085/identify?token=" + token + "&currentIp=" + request.getRemoteAddr());
                map = JSON.parseObject(userJson, HashMap.class);
                status = (String) map.get("status");
            }
            //判断是否必须要验证成功
            String flag = "success";
            //验证成功
            if (successNecessary) {
                //验证失败 跳回登录页面 登录成功获取token
                if (!flag.equals(status)) {
                    response.sendRedirect("http://passport.gmall.com:8085/index?returnUrl=" + request.getRequestURL());
                    return false;
                }
            }
            request.setAttribute("memberId", map.get("memberId"));
            request.setAttribute("nickName", map.get("nickname"));
            if (StringUtils.isNotBlank(token)) {
                CookieUtil.setCookie(request, response, "oldToken", token, 60 * 60 * 2, true);
            }
        }
        return true;
    }
}
