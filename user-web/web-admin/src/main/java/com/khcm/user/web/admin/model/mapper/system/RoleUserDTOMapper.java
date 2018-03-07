package com.khcm.user.web.admin.model.mapper.system;

import com.khcm.user.service.dto.business.system.RoleDTO;
import com.khcm.user.service.dto.business.system.RoleUserDTO;
import com.khcm.user.service.param.business.system.RoleParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.RolePM;
import com.khcm.user.web.admin.model.viewmodel.business.system.RoleVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Created by liuwenqi on 2018/2/1.
 */
@Mapper
public interface RoleUserDTOMapper extends DTOMapper<RoleUserDTO, RoleVM, RolePM, RoleParam> {
    RoleUserDTOMapper MAPPER = Mappers.getMapper(RoleUserDTOMapper.class);

    @Override
    @Mapping(target = "authorizations", expression = "java(AuthorizationDTOMapper.MAPPER.dtoToVM(roleUserDTO.getAuthorizations()))")
    RoleVM dtoToVM(RoleUserDTO roleUserDTO);
}
