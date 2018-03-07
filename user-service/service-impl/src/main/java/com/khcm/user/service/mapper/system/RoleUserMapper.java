package com.khcm.user.service.mapper.system;

import com.khcm.user.dao.entity.business.system.Role;
import com.khcm.user.dao.entity.business.system.User;
import com.khcm.user.service.dto.business.system.RoleDTO;
import com.khcm.user.service.dto.business.system.RoleUserDTO;
import com.khcm.user.service.dto.business.system.UserRoleDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.RoleParam;
import com.khcm.user.service.param.business.system.UserParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author liuwenqi
 */
@Mapper
public interface RoleUserMapper extends EntityMapper<Role,RoleUserDTO,RoleParam> {
    RoleUserMapper MAPPER = Mappers.getMapper(RoleUserMapper.class);

    @Override
    @Mapping(target = "authorizations", expression = "java(AuthorizationMapper.MAPPER.entityToDTO(role.getAuthorizations()))")
    @Mapping(target = "userIds", expression = "java(getUserIds(role.getUsers()))")
    RoleUserDTO entityToDTO(Role role);

    default List<Integer> getUserIds(Set<User> users){
        List<Integer> userIds =new ArrayList<>();
        Optional<Set<User>> userSet = Optional.ofNullable(users);
        userSet.ifPresent(users1 -> users1.forEach(user -> userIds.add(user.getId())));
        return userIds;
    }
}
