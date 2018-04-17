package com.khcm.user.web.admin.controller;

import com.khcm.user.common.utils.SpringUtils;
import com.khcm.user.service.api.system.ConfigService;
import com.khcm.user.service.api.system.UserService;
import com.khcm.user.service.dto.business.system.ConfigLoginDTO;
import com.khcm.user.web.common.shiro.IncorrectCaptchaException;
import com.khcm.user.web.common.shiro.RequiredUsernameException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录 Controller
 *
 */
@Controller
public class LoginController{

    public static final String VIEW_NAME_LOGIN = "login";

    public static final String ERROR_KEY = "errorInfo";

    public static final String ERROR_MSG_KEY_CAPTCHA_ERROR = "login.error.captcha.error";
    public static final String ERROR_MSG_KEY_USERNAME_REQUIRED = "login.error.username.required";
    public static final String ERROR_MSG_KEY_USER_NOTEXIST = "login.error.user.notexist";
    public static final String ERROR_MSG_KEY_PASSWORD_ERROR = "login.error.password.error";
    public static final String ERROR_MSG_KEY_USER_DISABLED = "login.error.user.disabled";
    public static final String ERROR_MSG_KEY_USER_LOCKED = "login.error.user.locked";
    public static final String ERROR_MSG_KEY_USER_UNNORMAL = "login.error.user.unnormal";
    public static final String ERROR_MSG_KEY_LOGIN_ERROR = "login.error";

    @Autowired
    private ConfigService configService;

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String input(Model model) {

        ConfigLoginDTO loginConfig = configService.getLoginConfig();
        model.addAttribute("errorTimes", loginConfig.getErrorTimes());

        return VIEW_NAME_LOGIN;
    }

    @PostMapping("login")
    public String submit(String username, HttpServletRequest request, Model model) {

        Integer errorRemaining= userService.errorRemaining(username);
        model.addAttribute("errorRemaining", errorRemaining);

        String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);

        //验证码错误
        if (StringUtils.equals(IncorrectCaptchaException.class.getName(), exception)) {
            model.addAttribute(ERROR_KEY, SpringUtils.getMessage(ERROR_MSG_KEY_CAPTCHA_ERROR));
            return VIEW_NAME_LOGIN;
        }

        //参数校验不通过
        if (StringUtils.equals(RequiredUsernameException.class.getName(), exception)) {
            model.addAttribute(ERROR_KEY, SpringUtils.getMessage(ERROR_MSG_KEY_USERNAME_REQUIRED));
            return VIEW_NAME_LOGIN;
        }

        //帐户不存在
        if (StringUtils.equals(UnknownAccountException.class.getName(), exception)) {
            model.addAttribute(ERROR_KEY, SpringUtils.getMessage(ERROR_MSG_KEY_USER_NOTEXIST));
            return VIEW_NAME_LOGIN;
        }

        //密码不正确
        if (StringUtils.equals(IncorrectCredentialsException.class.getName(), exception)) {
            model.addAttribute(ERROR_KEY, SpringUtils.getMessage(ERROR_MSG_KEY_PASSWORD_ERROR));
            return VIEW_NAME_LOGIN;
        }

        //帐号被禁用
        if (StringUtils.equals(DisabledAccountException.class.getName(), exception)) {
            model.addAttribute(ERROR_KEY, SpringUtils.getMessage(ERROR_MSG_KEY_USER_DISABLED));
            return VIEW_NAME_LOGIN;
        }

        //帐号被锁定
        if (StringUtils.equals(LockedAccountException.class.getName(), exception)) {
            model.addAttribute(ERROR_KEY, SpringUtils.getMessage(ERROR_MSG_KEY_USER_LOCKED));
            return VIEW_NAME_LOGIN;
        }

        //帐号异常，未激活或已关闭
        if (StringUtils.equals(AccountException.class.getName(), exception)) {
            model.addAttribute(ERROR_KEY, SpringUtils.getMessage(ERROR_MSG_KEY_USER_UNNORMAL));
            return VIEW_NAME_LOGIN;
        }

        model.addAttribute(ERROR_KEY, SpringUtils.getMessage(ERROR_MSG_KEY_LOGIN_ERROR));
        return VIEW_NAME_LOGIN;
    }

}
