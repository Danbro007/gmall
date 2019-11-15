package com.danbro.gmall.passport.web.thirdSocialAccountLogin;

/**
 * @author Danrbo
 * @date 2019/11/14 16:22
 * description
 **/

public enum OauthUser {
    /**
     * 微博用户
     */
    WEIBO_USER(1),
    /**
     * 微信用户
     */
    WEIXIN_USER(2);

    private Integer type;

    OauthUser(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
