package com.khcm.user.dao.repository.master.sms;

import com.khcm.user.dao.entity.business.sms.SmsCode;
import com.khcm.user.dao.repository.BaseRepository;

/**
 * Created by yangwb on 2018/3/1.
 */
public interface SmsCodeRepository extends BaseRepository<SmsCode, Integer> {
    SmsCode findByPhoneEqualsAndCodeEquals(String phone,String code);
}
