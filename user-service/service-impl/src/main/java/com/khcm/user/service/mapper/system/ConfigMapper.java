package com.khcm.user.service.mapper.system;

import com.khcm.user.dao.entity.business.system.Config;
import com.khcm.user.service.dto.business.system.ConfigDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.ConfigParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConfigMapper extends EntityMapper<Config, ConfigDTO, ConfigParam> {

    ConfigMapper MAPPER = Mappers.getMapper(ConfigMapper.class);

}
