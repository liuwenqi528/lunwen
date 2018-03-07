package com.khcm.user.service.mapper.system;

import com.khcm.user.dao.entity.business.system.Role;
import com.khcm.user.dao.entity.business.system.User;
import com.khcm.user.service.dto.business.system.RoleDTO;
import com.khcm.user.service.dto.business.system.RoleUserDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.RoleParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色 DTO 转换类
 *
 * @author wangtao
 * @date 2017/10/24
 */
@Mapper
public interface RoleMapper extends EntityMapper<Role, RoleDTO, RoleParam> {

    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);
    @Override
    @Mapping(target = "userIds", expression = "java(getUserIds(role.getUsers()))")
    RoleDTO entityToDTO(Role role);
    default List<Integer> getUserIds(Set<User> users){
        List<Integer> userIds =new ArrayList<>();
        Optional<Set<User>> userSet = Optional.ofNullable(users);
        userSet.ifPresent(users1 -> users1.forEach(user -> userIds.add(user.getId())));
        return userIds;
    }
}
