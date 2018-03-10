package com.khcm.user.web.admin.shiro;

import com.khcm.user.service.api.log.LogService;
import com.khcm.user.service.api.system.UserService;
import com.khcm.user.web.admin.config.AdminConfig;
import com.khcm.user.web.common.shiro.IncorrectCaptchaException;
import com.khcm.user.web.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理 AJAX 登录
 * 如果是 ajax 请求则直接处理，之后直接将信息通过response返回给客户端
 */
@Slf4j
public class AdminFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final String CAPTCHA_PARAM = "captcha";

    public static final String CAPTCHA_SESSION_KEY = "verifyCode";

    /** 自定义保存到session中的请求URI的key 用于页面跳转 */
    public static final String CUSTOM_SAVED_REQUEST = "customSavedRequest";
    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @Autowired
    private AdminConfig adminConfig;

    public String getCaptchaParam() {
        return CAPTCHA_PARAM;
    }

    protected String getCaptcha(ServletRequest request) {
        return org.apache.shiro.web.util.WebUtils.getCleanParam(request, getCaptchaParam());
    }

    @Override
    protected CaptchaAuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        return new CaptchaAuthenticationToken(username, password, rememberMe, host, captcha);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        CaptchaAuthenticationToken token = createToken(request, response);

        try {
            //校验验证码
            doCaptchaValidate((HttpServletRequest) request, token);

            //登录
            Subject subject = getSubject(request, response);
            subject.login(token);
            SavedRequest savedRequest = org.apache.shiro.web.util.WebUtils.getAndClearSavedRequest(request);
            Session session = subject.getSession();
            session.setAttribute(CUSTOM_SAVED_REQUEST, savedRequest);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    /**
     * 登录成功
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
                                     ServletRequest request, ServletResponse response) throws Exception {
        CaptchaAuthenticationToken cToken = (CaptchaAuthenticationToken) token;
        HttpServletRequest req = (HttpServletRequest) request;

        userService.loginSuccess(cToken.getUsername(), WebUtils.getIpAddr(req));
        logService.createLoginSuccessLog(cToken.getUsername(), adminConfig.getAppCode(), "username=" + cToken.getUsername(), WebUtils.getIpAddr(req));

        issueSuccessRedirect(request, response);

        return false;
    }

    /**
     * 登录失败
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        CaptchaAuthenticationToken cToken = (CaptchaAuthenticationToken) token;
        HttpServletRequest req = (HttpServletRequest) request;

        if (log.isDebugEnabled()) {
            log.debug("Authentication exception", e);
        }
        setFailureAttribute(request, e);

        userService.loginFailaure(cToken.getUsername(), WebUtils.getIpAddr(req));
        logService.createLoginFailureLog(cToken.getUsername(), adminConfig.getAppCode(), "username=" + cToken.getUsername(), WebUtils.getIpAddr(req));

        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }

            //保存请求
            saveRequest(request);

            //Ajax登录
            if (WebUtils.isAjaxRequest((HttpServletRequest) request)) {
                HttpServletResponse resp= (HttpServletResponse) response;
                resp.setHeader("loginStatus", "0");
                Map<String, Object> jsonMap = new HashMap<>();
                jsonMap.put("loginMessage", "未登录或登录已过期");
                WebUtils.writeJSON(resp, jsonMap);
                return false;
            }

            //重定向登录
            redirectToLogin(request, response);
            return false;
        }
    }

    protected void doCaptchaValidate(HttpServletRequest request, CaptchaAuthenticationToken token) {
        String sessionCaptcha = (String) request.getSession().getAttribute(CAPTCHA_SESSION_KEY);

        if (StringUtils.isNotBlank(sessionCaptcha) && !StringUtils.equals(sessionCaptcha, token.getCaptcha())) {
            throw new IncorrectCaptchaException("验证码错误");
        }
    }

}
