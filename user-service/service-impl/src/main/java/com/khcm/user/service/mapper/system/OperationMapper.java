package com.khcm.user.service.mapper.system;

import com.khcm.user.dao.entity.business.system.Resource;
import com.khcm.user.service.dto.business.system.OperationDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.OperationParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Created by yangwb on 2017/12/4.
 */
@Mapper
public interface OperationMapper extends EntityMapper<Resource, OperationDTO, OperationParam> {

    OperationMapper MAPPER = Mappers.getMapper(OperationMapper.class);

    @Override
    @Mapping(target = "text", source = "name")
    OperationDTO entityToDTO(Resource entity);

}

