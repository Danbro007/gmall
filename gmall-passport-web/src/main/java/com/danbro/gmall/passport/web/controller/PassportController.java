package com.danbro.gmall.passport.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Danrbo
 * @date 2019/11/6 12:15
 * description
 **/

@Controller
public class PassportController {

    @GetMapping("/index")
    public String index(Model model, String returnUrl) {
        model.addAttribute("returnUrl", returnUrl);
        return "index";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login() {
        return "token";
    }

    @GetMapping("/identify")
    @ResponseBody
    public String identify(String token){
        if (StringUtils.isNotBlank(token)){
            return "success";
        }
        return "fail";
    }
}
