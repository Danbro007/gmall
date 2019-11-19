package com.danbro.gmall.passport.web.thirdSocialAccountLogin.userProvider;

import com.alibaba.fastjson.JSON;
import com.danbro.gmall.api.po.MemberPo;
import com.danbro.gmall.common.utils.util.HttpClientUtil;
import com.danbro.gmall.common.utils.util.ObjectUtil;
import com.danbro.gmall.passport.web.thirdSocialAccountLogin.LoginInterface;
import com.danbro.gmall.passport.web.thirdSocialAccountLogin.OauthUser;
import com.danbro.gmall.passport.web.thirdSocialAccountLogin.accessToken.AccessToken;
import com.danbro.gmall.passport.web.thirdSocialAccountLogin.accessToken.WeiboAccessToken;
import com.danbro.gmall.passport.web.thirdSocialAccountLogin.postParam.PostParam;
import com.danbro.gmall.passport.web.thirdSocialAccountLogin.postParam.WeiboPostParam;

import java.util.Map;

/**
 * @author Danrbo
 * @date 2019/11/12 21:06
 * description 新浪微博账户登录
 **/
public class WeiboUserProvider implements LoginInterface {
    @Override
    public WeiboAccessToken getAccessToken(PostParam postParam) {
        WeiboPostParam weiboPostParam = (WeiboPostParam) postParam;
        String token  =  HttpClientUtil.doPost("https://api.weibo.com/oauth2/access_token", ObjectUtil.objectToMap(weiboPostParam));

        return JSON.parseObject(token, WeiboAccessToken.class);
    }


    @Override
    public MemberPo getUserInfo(AccessToken accessToken) {
        WeiboAccessToken weiboAccessToken = (WeiboAccessToken) accessToken;
        String weiboUserInfo = HttpClientUtil.doGet("https://api.weibo.com/2/users/show.json?access_token=" + weiboAccessToken.getAccessToken() + "&" + "uid=" + weiboAccessToken.getUid());
        Map userInfoMap = JSON.parseObject(weiboUserInfo, Map.class);
        MemberPo memberPo = new MemberPo();
        memberPo.setCity((String) userInfoMap.get("location"));
        memberPo.setAccessToken(weiboAccessToken.getAccessToken());
        memberPo.setAccessCode(weiboAccessToken.getCode());
        Integer gender = userInfoMap.get("gender").equals("m") ? 1 : 2;
        memberPo.setGender(gender);
        memberPo.setSourceUid((String) userInfoMap.get("idstr"));
        memberPo.setNickname((String) userInfoMap.get("screen_name"));
        memberPo.setIcon((String) userInfoMap.get("profile_image_url"));
        memberPo.setSourceType(OauthUser.WEIBO_USER.getType());
        return memberPo;
    }


}
