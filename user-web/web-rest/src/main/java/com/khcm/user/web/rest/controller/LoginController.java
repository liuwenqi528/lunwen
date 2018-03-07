package com.khcm.user.web.rest.controller;

import com.khcm.user.common.enums.BusinessType;
import com.khcm.user.service.api.sms.SmsService;
import com.khcm.user.web.rest.model.viewmodel.base.ResultVM;
import com.khcm.user.web.rest.model.viewmodel.business.login.LoginVM;
import com.khcm.user.web.rest.utils.VMUtils;
import org.apache.commons.lang3.StringUtils;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.profile.CasProfile;
import org.pac4j.cas.profile.CasRestProfile;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private CasRestFormClient casRestFormClient;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Value("${shiro.cas.serviceUrl}")
    private String serviceUrl;

    @Autowired
    private SmsService smsService;

    /**
     * 发送短信验证码
     *
     * @return
     */
    @GetMapping("sendSmsCode")
    public ResultVM sendSmsCode(String phone) {
        smsService.sendSmsCode(phone, BusinessType.LOGIN);
        return VMUtils.resultSuccess();
    }

    @PostMapping
    public LoginVM login(HttpServletRequest request, HttpServletResponse response) {
        J2EContext context = new J2EContext(request, response);
        ProfileManager<CasRestProfile> manager = new ProfileManager(context);
        Optional<CasRestProfile> profile = manager.get(true);

        TokenCredentials tokenCredentials = casRestFormClient.requestServiceTicket(serviceUrl, profile.get(), context);
        CasProfile casProfile = casRestFormClient.validateServiceTicket(serviceUrl, tokenCredentials, context);
        String token = jwtGenerator.generate(casProfile);

        LoginVM loginVM = new LoginVM();

        loginVM.setJwtToken(token);
        return loginVM;
    }

}
