package com.khcm.user.web.admin.model.mapper.system;

import com.khcm.user.service.dto.business.system.RoleDTO;
import com.khcm.user.service.param.business.system.AppParam;
import com.khcm.user.service.param.business.system.RoleParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.AppPM;
import com.khcm.user.web.admin.model.parammodel.business.system.RolePM;
import com.khcm.user.web.admin.model.viewmodel.business.system.RoleVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Created by wangtao on 2018/1/11.
 */
@Mapper
public interface RoleDTOMapper extends DTOMapper<RoleDTO, RoleVM, RolePM, RoleParam> {
    RoleDTOMapper MAPPER = Mappers.getMapper(RoleDTOMapper.class);

    @Override
    @Mapping(target = "beginTime", expression = "java(com.khcm.user.web.admin.utils.MapperUtils.parseToDatetime(rolePM.getBeginTime()))")
    @Mapping(target = "endTime", expression = "java(com.khcm.user.web.admin.utils.MapperUtils.parseToDatetime(rolePM.getEndTime()))")
    RoleParam pmToParam(RolePM rolePM);
}
