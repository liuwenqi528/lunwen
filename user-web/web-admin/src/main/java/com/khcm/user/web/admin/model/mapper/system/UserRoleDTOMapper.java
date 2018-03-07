package com.khcm.user.web.admin.model.mapper.system;

import com.khcm.user.service.dto.business.system.UserRoleDTO;
import com.khcm.user.service.param.business.system.UserParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.UserPM;
import com.khcm.user.web.admin.model.viewmodel.business.system.UserVM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface UserRoleDTOMapper extends DTOMapper<UserRoleDTO, UserVM, UserPM, UserParam> {
    UserRoleDTOMapper MAPPER = Mappers.getMapper(UserRoleDTOMapper.class);
}
