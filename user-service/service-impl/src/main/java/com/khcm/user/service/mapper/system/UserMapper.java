package com.khcm.user.service.mapper.system;

import com.khcm.user.common.constant.Constants;
import com.khcm.user.dao.entity.business.system.Role;
import com.khcm.user.dao.entity.business.system.User;
import com.khcm.user.service.dto.business.system.UserDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.UserParam;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * 用户信息 DTO 转换类
 *
 * @author wangtao
 * @date 2017/10/25
 */
@Mapper
public interface UserMapper extends EntityMapper<User, UserDTO, UserParam> {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Override
    @Mapping(target = "nickname", source = "ext.nickname")
    @Mapping(target = "realname", source = "ext.realname")
    @Mapping(target = "birthday", source = "ext.birthday")
    @Mapping(target = "idcard", source = "ext.idcard")
    @Mapping(target = "age", source = "ext.age")
    @Mapping(target = "sex", source = "ext.sex")
    @Mapping(target = "roleIds", expression = "java(getRoleIds(user.getRoles()))")
    UserDTO entityToDTO(User user);

    @Override
    @Mapping(source = "nickname", target = "ext.nickname")
    @Mapping(source = "realname", target = "ext.realname")
    @Mapping(target = "password", expression = "java(getMD5Password(userParam))")
    User paramToEntity(UserParam userParam);

    @Override
    @Mapping(target = "ext.nickname", source = "nickname")
    @Mapping(target = "ext.realname", source = "realname")
    User paramToEntity(UserParam userParam, @MappingTarget User user);


    default String getMD5Password(UserParam userParam) {
        String cryPassword = new SimpleHash(Constants.HASH_ALGORITHM_NAME, userParam.getPassword(), userParam.getUsername(), Constants.HASH_ITERATIONS).toString();
        return cryPassword;
    }

    default List<Integer> getRoleIds(Set<Role> roles) {
        List<Integer> roleIds = new ArrayList<>();
        if (Objects.nonNull(roles)) {
            roles.forEach(role -> {
                roleIds.add(role.getId());
            });
        }
        return roleIds;
    }
}
