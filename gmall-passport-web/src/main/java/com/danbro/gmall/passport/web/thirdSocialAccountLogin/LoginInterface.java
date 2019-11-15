package com.danbro.gmall.passport.web.thirdSocialAccountLogin;

import com.danbro.gmall.api.po.MemberPo;
import com.danbro.gmall.passport.web.thirdSocialAccountLogin.accessToken.AccessToken;
import com.danbro.gmall.passport.web.thirdSocialAccountLogin.postParam.PostParam;
import com.danbro.gmall.passport.web.thirdSocialAccountLogin.user.User;

/**
 * @author Danrbo
 * @date 2019/11/13 14:43
 * description 第三方账户登录接口
 **/
public interface LoginInterface {
    /**
     * 通过获取到的code发请求获取token
     * @param postParam 请求参数
     * @return token
     */
    AccessToken getAccessToken(PostParam postParam);

    /**
     * 通过token获得用户信息
     * @param accessToken token
     * @return 用户信息
     */
    MemberPo getUserInfo(AccessToken accessToken);

}
