package com.khcm.user.service.mapper.system;

import com.khcm.user.dao.entity.business.system.App;
import com.khcm.user.dao.entity.business.system.Authorization;
import com.khcm.user.dao.entity.business.system.Resource;
import com.khcm.user.dao.entity.business.system.Role;
import com.khcm.user.service.dto.business.system.AuthorizationDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.AuthorizationParam;
import com.khcm.user.service.param.business.system.ResourceParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

/**
 * Created by yangwb on 2017/12/4.
 */
@Mapper
public interface AuthorizationMapper extends EntityMapper<Authorization, AuthorizationDTO, AuthorizationParam> {

    AuthorizationMapper MAPPER = Mappers.getMapper(AuthorizationMapper.class);

    @Override
    @Mapping(target = "roleId", source = "role.id")
    @Mapping(target = "appId", source = "app.id")
    @Mapping(target = "resourceId", source = "resource.id")
    AuthorizationDTO entityToDTO(Authorization entity);

    @Override
    @Mapping(target = "role", expression = "java(getRole(authorizationParam.getRoleId()))")
    @Mapping(target = "app", expression = "java(getApp(authorizationParam.getAppId()))")
    @Mapping(target = "resource", expression = "java(getResource(authorizationParam.getResourceId()))")
    Authorization paramToEntity(AuthorizationParam authorizationParam);

    default Role getRole(Integer roleId) {
        if (roleId == null || roleId <= 0) {
            return null;
        }
        Role role = new Role();
        role.setId(roleId);
        return role;
    }

    default App getApp(Integer appId) {
        if (appId == null || appId <= 0) {
            return null;
        }
        App app = new App();
        app.setId(appId);
        return app;
    }

    default Resource getResource(Integer resourceId) {
        if (resourceId == null || resourceId <= 0) {
            return null;
        }
        Resource resource = new Resource();
        resource.setId(resourceId);
        return resource;
    }

}
