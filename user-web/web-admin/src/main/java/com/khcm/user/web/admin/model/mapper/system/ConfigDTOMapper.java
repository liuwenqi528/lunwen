package com.khcm.user.web.admin.model.mapper.system;

import com.khcm.user.service.dto.business.system.ConfigDTO;
import com.khcm.user.service.param.business.system.ConfigParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.ConfigPM;
import com.khcm.user.web.admin.model.viewmodel.business.system.ConfigVM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wangtao
 * @date 2018/1/11.
 */
@Mapper
public interface ConfigDTOMapper extends DTOMapper<ConfigDTO, ConfigVM, ConfigPM, ConfigParam> {
    ConfigDTOMapper MAPPER = Mappers.getMapper(ConfigDTOMapper.class);
}
