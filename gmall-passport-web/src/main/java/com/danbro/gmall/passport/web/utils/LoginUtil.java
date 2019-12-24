package com.danbro.gmall.passport.web.utils;

import com.danbro.gmall.api.po.MemberPo;
import com.danbro.gmall.api.service.MemberService;
import com.danbro.gmall.common.utils.util.CookieUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname LoginUtil
 * @Description TODO
 * @Date 2019/12/4 20:34
 * @Author Danrbo
 */
public class LoginUtil {

    public static void loginByToken(MemberPo memberPo, HttpServletRequest request, String token, String defaultIp, String tokenKey, MemberService memberService) {
        token = TokenUtil.getToken(memberPo, request, defaultIp, tokenKey);
        String fail = "fail";
        if (!token.equals(fail)) {
            String cartListFromCache = CookieUtil.getCookieValue(request, "cartListCookie", true);
            memberService.addUserToken(memberPo.getId(), token, cartListFromCache);
        }
    }
}
