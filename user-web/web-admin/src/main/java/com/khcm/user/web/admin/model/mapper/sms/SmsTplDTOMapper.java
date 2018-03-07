package com.khcm.user.web.admin.model.mapper.sms;

import com.khcm.user.service.dto.business.sms.SmsTplDTO;
import com.khcm.user.service.param.business.sms.SmsTplParam;
import com.khcm.user.service.param.business.system.RoleParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.mapper.system.AppDTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.sms.SmsTplPM;
import com.khcm.user.web.admin.model.parammodel.business.system.RolePM;
import com.khcm.user.web.admin.model.viewmodel.business.sms.SmsTplVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Created by
 *
 * @author: liuwenqi on 2018-03-01.
 * Description:
 */
@Mapper
public interface SmsTplDTOMapper extends DTOMapper<SmsTplDTO, SmsTplVM, SmsTplPM, SmsTplParam> {
    SmsTplDTOMapper MAPPER = Mappers.getMapper(SmsTplDTOMapper.class);

    @Override
    @Mapping(target = "beginTime", expression = "java(com.khcm.user.web.admin.utils.MapperUtils.parseToDatetime(smsTplPM.getBeginTime()))")
    @Mapping(target = "endTime", expression = "java(com.khcm.user.web.admin.utils.MapperUtils.parseToDatetime(smsTplPM.getEndTime()))")
    SmsTplParam pmToParam(SmsTplPM smsTplPM);
}
