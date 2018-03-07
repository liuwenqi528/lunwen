package com.khcm.user.service.mapper.system;

import com.khcm.user.dao.entity.business.system.Area;
import com.khcm.user.service.dto.business.system.AreaDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.AreaParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Created by rqn on 2018/1/12.
 */
@Mapper
public interface AreaMapper extends EntityMapper<Area, AreaDTO, AreaParam> {
    AreaMapper MAPPER = Mappers.getMapper(AreaMapper.class);

    @Override
    @Mapping(target = "text", source = "name")
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentName", source = "parent.name")
    AreaDTO entityToDTO(Area area);

    @Override
    @Mapping(target = "parent", expression = "java(getParent(areaParam.getParentId()))")
    Area paramToEntity(AreaParam areaParam);

    default Area getParent(Integer parentId) {
        if (parentId == null || parentId <= 0) {
            return null;
        }
        Area area = new Area();
        area.setId(parentId);
        return area;
    }
}
