package com.danbro.gmall.passport.web.utils;

import com.danbro.gmall.api.po.MemberPo;
import com.danbro.gmall.common.utils.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author Danrbo
 * @date 2019/11/14 16:47
 * description
 **/


public class TokenUtil {

    public static String getToken(MemberPo loginMember, HttpServletRequest request,String defaultIp,String tokenKey){
        String token;
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
            userMap.put("memberId", Long.toString(memberId));
            //jwt token加密
            token = JwtUtil.encode(tokenKey, userMap, DigestUtils.md5DigestAsHex(ip.getBytes()));

        } else {
            token = "fail";
        }
        return token;
    }


}
