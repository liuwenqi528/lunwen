package com.khcm.user.web.rest.controller;

import com.khcm.user.common.enums.BusinessType;
import com.khcm.user.service.api.sms.SmsService;
import com.khcm.user.service.api.system.UserService;
import com.khcm.user.web.rest.model.viewmodel.base.ResultVM;
import com.khcm.user.web.rest.utils.VMUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangtao on 2018/3/6
 */
@RestController
@RequestMapping("regist")
public class RegistController {

    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;

    /**
     * 发送短信验证码
     *
     * @return
     */
    @GetMapping("sendSmsCode")
    public ResultVM sendSmsCode(String phone) {
        smsService.sendSmsCode(phone, BusinessType.REGIST);
        return VMUtils.resultSuccess();
    }

    /**
     * 提交注册
     *
     * @return
     */
    @PostMapping
    public Object submit() {
        return null;
    }


}
