package com.khcm.user.service.mapper.system;

import com.khcm.user.dao.entity.business.system.User;
import com.khcm.user.dao.entity.business.system.UserExt;
import com.khcm.user.service.dto.business.system.UserDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.UserInfoParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author rqn on 2018/2/1.
 */
@Mapper
public interface UserInfoMapper extends EntityMapper<User, UserDTO, UserInfoParam> {

    UserInfoMapper MAPPER = Mappers.getMapper(UserInfoMapper.class);

    @Override
    @Mapping(target = "ext.nickname", source = "nickname")
    @Mapping(target = "ext.realname", source = "realname")
    @Mapping(target = "ext.birthday", source = "birthday")
    @Mapping(target = "ext.idcard", source = "idcard")
    @Mapping(target = "ext.age", source = "age")
    @Mapping(target = "ext.sex", expression = "java(setSex(userInfoParam))")
    User paramToEntity(UserInfoParam userInfoParam, @MappingTarget User user);

    default UserExt.Sex setSex(UserInfoParam userInfoParam) {
        if (UserExt.Sex.MALE.toString().equals(userInfoParam.getSex())) {
            return UserExt.Sex.MALE;
        } else if (UserExt.Sex.FEMALE.toString().equals(userInfoParam.getSex())) {
            return UserExt.Sex.FEMALE;
        } else {
            return UserExt.Sex.UNKOWN;
        }
    }
}
