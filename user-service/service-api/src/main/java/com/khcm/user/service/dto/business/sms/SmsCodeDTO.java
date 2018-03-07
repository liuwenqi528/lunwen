package com.khcm.user.service.dto.business.sms;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rqn on 2018/3/1.
 */
@Getter
@Setter
public class SmsCodeDTO implements Serializable {
    private String phone;
    private String code;
    private Date expiredTime;
}
