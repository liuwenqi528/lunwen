package com.khcm.user.service.mapper.sms;

import com.khcm.user.dao.entity.business.sms.SmsCode;
import com.khcm.user.service.dto.business.sms.SmsCodeDTO;
import com.khcm.user.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by IntelliJ IDEA.
 * User: rqn
 * Date: 2018/3/1
 * Time: 14:36
 */
@Mapper
public interface SmsCodeMapper extends EntityMapper<SmsCode,SmsCodeDTO,Object> {
    SmsCodeMapper MAPPER = Mappers.getMapper(SmsCodeMapper.class);
}
