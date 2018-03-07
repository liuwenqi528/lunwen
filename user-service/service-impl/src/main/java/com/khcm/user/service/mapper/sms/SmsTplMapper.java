package com.khcm.user.service.mapper.sms;

import com.khcm.user.dao.entity.business.system.Template;
import com.khcm.user.service.dto.business.sms.SmsTplDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.sms.SmsTplParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by IntelliJ IDEA.
 * User: rqn
 * Date: 2018/3/1
 * Time: 14:36
 */
@Mapper
public interface SmsTplMapper extends EntityMapper<Template,SmsTplDTO,SmsTplParam> {
    SmsTplMapper MAPPER = Mappers.getMapper(SmsTplMapper.class);
}
