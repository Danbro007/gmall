package com.danbro.gmall.passport.web.thirdSocialAccountLogin.accessToken;

import lombok.Data;

/**
 * @author Danrbo
 * @date 2019/11/12 21:08
 * description
 **/
@Data
public class WeiboAccessToken extends AccessToken{
    private String uid;
    private String remindIn;
    private String expiresIn;
    private String isRealName;
}
