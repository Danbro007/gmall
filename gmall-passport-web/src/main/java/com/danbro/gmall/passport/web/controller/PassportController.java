package com.danbro.gmall.passport.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.danbro.gmall.api.po.MemberPo;
import com.danbro.gmall.api.service.CartService;
import com.danbro.gmall.api.service.MemberService;
import com.danbro.gmall.common.utils.annotations.LoginRequired;
import com.danbro.gmall.common.utils.util.CookieUtil;
import com.danbro.gmall.common.utils.util.JwtUtil;
import com.danbro.gmall.passport.web.thirdSocialAccountLogin.accessToken.WeiboAccessToken;
import com.danbro.gmall.passport.web.thirdSocialAccountLogin.postParam.WeiboPostParam;
import com.danbro.gmall.passport.web.thirdSocialAccountLogin.userProvider.WeiboUserProvider;
import com.danbro.gmall.passport.web.utils.LoginUtil;
import com.danbro.gmall.passport.web.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Danrbo
 * @date 2019/11/6 12:15
 * description
 **/

@Controller
public class PassportController {

    @Value("${token.key}")
    private String tokenKey;

    @Value("${default.ip}")
    private String defaultIp;

    @Value("${weibo.client.id}")
    private String clientId;

    @Value("${weibo.client.secret}")
    private String clientSecret;

    @Value("${weibo.redirect.uri}")
    private String redirectUri;

    @Value("${weibo.grant.type}")
    private String grantType;

    @Reference
    MemberService memberService;

    @Reference
    CartService cartService;

    @GetMapping("/index")
    public String index(Model model, String returnUrl) {
        model.addAttribute("returnUrl", returnUrl);
        return "index";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        MemberPo loginMember = memberService.login(username, password);
        //获取token
        String token = TokenUtil.getToken(loginMember, request, defaultIp, tokenKey);
        CookieUtil.setCookie(request, response, "oldToken", token, 60 * 60 * 2, true);
        //登录成功
        LoginUtil.loginByToken(loginMember, request, token, defaultIp, tokenKey, memberService);
        return token;
    }

    @GetMapping("/identify")
    @ResponseBody
    public String identify(String token, String ip) {
        HashMap<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(ip)) {
            ip = defaultIp;
        }
        String ipMd5 = DigestUtils.md5DigestAsHex(ip.getBytes());
        Map<String, Object> decode = JwtUtil.decode(token, tokenKey, ipMd5);
        if (decode != null) {
            map.put("nickname", decode.get("nickname"));
            map.put("memberId", decode.get("memberId"));
            map.put("status", true);
        } else {
            map.put("status", false);
        }

        return JSON.toJSONString(map);
    }


    @GetMapping("/vlogin")
    public String weiboLogin(@Param("code") String code, HttpServletRequest request) {
        WeiboPostParam weiboPostParam = new WeiboPostParam();
        weiboPostParam.setClientId(clientId);
        weiboPostParam.setClientSecret(clientSecret);
        weiboPostParam.setGrantType(grantType);
        weiboPostParam.setCode(code);
        weiboPostParam.setRedirectUri(redirectUri);
        WeiboUserProvider weiboUserProvider = new WeiboUserProvider();
        //获取token
        String token = "";
        WeiboAccessToken weiboAccessToken = weiboUserProvider.getAccessToken(weiboPostParam);
        if (weiboAccessToken != null) {
            //通过token获取账户信息
            weiboAccessToken.setCode(code);
            MemberPo memberPo = weiboUserProvider.getUserInfo(weiboAccessToken);
            MemberPo checkMember = new MemberPo();
            checkMember.setSourceType(memberPo.getSourceType());
            checkMember.setSourceUid(memberPo.getSourceUid());

            MemberPo checkMemberFromDb = memberService.checkOauthUser(checkMember);
            //判断此用户在数据库是否存在
            if (checkMemberFromDb == null) {
//            //不存在添加到数据库中
                memberPo = memberService.addOauthUser(memberPo);
            } else {
                memberPo = checkMemberFromDb;
            }
            //获取token
            LoginUtil.loginByToken(memberPo, request, token, defaultIp, tokenKey, memberService);
        }
        return "redirect:http://search.gmall.com:8082/index?token=" + token;

    }

}
