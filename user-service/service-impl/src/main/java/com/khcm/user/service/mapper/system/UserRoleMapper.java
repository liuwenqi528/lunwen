package com.khcm.user.service.mapper.system;

import com.khcm.user.dao.entity.business.system.User;
import com.khcm.user.service.dto.business.system.UserRoleDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.UserParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by rqn on
 */
@Mapper
public interface UserRoleMapper extends EntityMapper<User,UserRoleDTO,UserParam> {
    UserRoleMapper MAPPER = Mappers.getMapper(UserRoleMapper.class);
}
