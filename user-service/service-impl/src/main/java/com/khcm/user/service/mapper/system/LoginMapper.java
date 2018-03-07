package com.khcm.user.service.mapper.system;

import com.khcm.user.dao.entity.business.system.User;
import com.khcm.user.service.dto.business.system.LoginDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.UserParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by rqn on
 */
@Mapper
public interface LoginMapper extends EntityMapper<User,LoginDTO,UserParam> {

    LoginMapper MAPPER = Mappers.getMapper(LoginMapper.class);

}
