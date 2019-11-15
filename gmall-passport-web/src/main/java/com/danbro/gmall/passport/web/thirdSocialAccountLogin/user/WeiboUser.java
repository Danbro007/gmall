package com.danbro.gmall.passport.web.thirdSocialAccountLogin.user;

import lombok.Data;

/**
 * @author Danrbo
 * @date 2019/11/13 14:51
 * description
 **/
@Data
public class WeiboUser extends User{

    private String id;
    private String profileImageUrl;
    private String name;
    private String location;
    private String avatarLarge;

}
