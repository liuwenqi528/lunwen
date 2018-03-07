package com.khcm.user.service.mapper.system;

import com.khcm.user.dao.entity.business.system.Config;
import com.khcm.user.service.dto.business.system.ConfigLoginDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.ConfigParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统配置
 * @author wangtao
 * on 2019/01/19
 */
@Mapper
public interface ConfigLoginMapper extends EntityMapper<Config, ConfigLoginDTO, ConfigParam> {

    ConfigLoginMapper MAPPER = Mappers.getMapper(ConfigLoginMapper.class);

}
