package com.khcm.user.service.mapper.system;

import com.khcm.user.dao.entity.business.system.Resource;
import com.khcm.user.service.dto.business.system.ModuleDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.ModuleParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Created by yangwb on 2017/12/4.
 */
@Mapper
public interface ModuleMapper extends EntityMapper<Resource, ModuleDTO, ModuleParam> {

    ModuleMapper MAPPER = Mappers.getMapper(ModuleMapper.class);

    @Override
    @Mapping(target = "text", source = "name")
    @Mapping(target = "children", ignore = true)
    ModuleDTO entityToDTO(Resource entity);

}
