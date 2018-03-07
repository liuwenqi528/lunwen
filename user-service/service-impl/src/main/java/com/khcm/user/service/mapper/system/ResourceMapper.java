package com.khcm.user.service.mapper.system;

import com.khcm.user.dao.entity.business.system.App;
import com.khcm.user.dao.entity.business.system.Resource;
import com.khcm.user.service.dto.business.system.ResourceDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.ResourceParam;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.persistence.Id;
import java.util.Optional;

/**
 * Created by yangwb on 2017/12/4.
 */
@Mapper(imports = {App.class, Resource.class})
public interface ResourceMapper extends EntityMapper<Resource, ResourceDTO, ResourceParam> {

    ResourceMapper MAPPER = Mappers.getMapper(ResourceMapper.class);

    @Override
    @Mapping(target = "text", source = "name")
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "appId", source = "app.id")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentName", source = "parent.name")
    ResourceDTO entityToDTO(Resource entity);

    @Override
    @Mapping(target = "parent", expression = "java(getParent(resourceParam.getParentId()))")
    @Mapping(target = "app", expression = "java(getApp(resourceParam.getAppId()))")
    Resource paramToEntity(ResourceParam resourceParam);

    default Resource getParent(Integer parentId) {
        if (parentId == null || parentId <= 0) {
            return null;
        }

        Resource resource = new Resource();
        resource.setId(parentId);
        return resource;
    }

    default App getApp(Integer appId) {
        Optional<Integer> optionalAppId = Optional.ofNullable(appId);
        return optionalAppId.map(id -> {
            App app = new App();
            app.setId(appId);
            return app;
        }).orElse(null);
    }

}
