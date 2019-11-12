package com.danbro.gmall.passport.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.danbro.gmall.api.po.MemberPo;
import com.danbro.gmall.api.service.MemberService;
import com.danbro.gmall.common.utils.JwtUtil;
import jdk.nashorn.internal.objects.annotations.Property;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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

    @Reference
    MemberService memberService;

    @GetMapping("/index")
    public String index(Model model, String returnUrl) {
        model.addAttribute("returnUrl", returnUrl);
        return "index";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(String username, String password, HttpServletRequest request) {
        String token = "";
        MemberPo loginMember = memberService.login(username, password);
        if (loginMember != null) {
            String ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isBlank(ip)) {
                ip = request.getRemoteAddr();
                if (StringUtils.isBlank(ip)) {
                    ip = defaultIp;
                }
            }
            HashMap<String, Object> userMap = new HashMap<>(16);
            String nickname = loginMember.getNickname();
            Long memberId = loginMember.getId();
            userMap.put("nickname", nickname);
            userMap.put("memberId", memberId);
            //jwt token加密
            token = JwtUtil.encode(tokenKey, userMap, DigestUtils.md5DigestAsHex(ip.getBytes()));
            memberService.addUserToken(memberId, token);
        } else {
            token = "fail";
        }
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
            map.put("status", "success");
        } else {
            map.put("status", "fail");
        }

        return JSON.toJSONString(map);
    }
}
