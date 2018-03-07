package com.khcm.user.web.rest.controller;

import com.khcm.user.service.api.sms.SmsService;
import com.khcm.user.web.rest.model.viewmodel.base.ResultVM;
import com.khcm.user.web.rest.utils.VMUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangtao on 2018/3/6
 */
@RestController
@RequestMapping("sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @GetMapping("sendMessage")
    public ResultVM sendMessage(String phone, String message) {
        smsService.sendMessage(phone, message);
        return VMUtils.resultSuccess();
    }

}
