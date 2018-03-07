package com.khcm.user.service.mapper.log;

import com.khcm.user.dao.entity.business.log.Log;
import com.khcm.user.dao.entity.business.system.User;
import com.khcm.user.service.dto.business.log.LogDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.log.LogParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {User.class,Log.class})
public interface LogMapper extends EntityMapper<Log,LogDTO,LogParam> {
    LogMapper MAPPER = Mappers.getMapper(LogMapper.class);
    @Override
    @Mapping(target = "username", source = "user.username")
    LogDTO entityToDTO(Log log);

    @Override
    Log paramToEntity(LogParam logParam);
}
