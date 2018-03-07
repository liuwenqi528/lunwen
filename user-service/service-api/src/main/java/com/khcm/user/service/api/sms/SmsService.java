package com.khcm.user.service.api.sms;

import com.khcm.user.common.enums.BusinessType;
import com.khcm.user.service.dto.business.sms.SmsCodeDTO;

import java.util.List;

public interface SmsService {

    void sendMessage(String phone, String content);

    int sendSmsCode(String phone, BusinessType businessType);

    SmsCodeDTO validateSmsCode(String phone, String smsCode);

}
