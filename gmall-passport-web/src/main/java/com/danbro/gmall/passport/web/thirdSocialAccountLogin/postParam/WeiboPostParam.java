package com.danbro.gmall.passport.web.thirdSocialAccountLogin.postParam;

import lombok.Data;

/**
 * @author Danrbo
 * @date 2019/11/13 14:14
 * description 请求微博token的参数类
 **/
@Data
public class WeiboPostParam extends PostParam {
    private String grantType;
    private String code;
    private String redirectUri;
}
