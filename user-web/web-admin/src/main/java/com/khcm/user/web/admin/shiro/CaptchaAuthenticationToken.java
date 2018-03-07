package com.khcm.user.web.admin.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by yangwb on 2018/1/3.
 */
public class CaptchaAuthenticationToken extends UsernamePasswordToken {

    private String captcha;

    public CaptchaAuthenticationToken() {
    }

    public CaptchaAuthenticationToken(String username, String password, boolean rememberMe, String host, String captcha) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}