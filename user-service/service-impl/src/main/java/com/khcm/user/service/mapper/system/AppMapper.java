package com.khcm.user.service.mapper.system;

import com.khcm.user.dao.entity.business.system.App;
import com.khcm.user.service.dto.business.system.AppDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.AppParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppMapper extends EntityMapper<App, AppDTO, AppParam> {

    AppMapper MAPPER = Mappers.getMapper(AppMapper.class);

}
