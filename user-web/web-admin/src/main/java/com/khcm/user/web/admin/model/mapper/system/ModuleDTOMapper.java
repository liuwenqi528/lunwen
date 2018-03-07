package com.khcm.user.web.admin.model.mapper.system;

import com.khcm.user.service.dto.business.system.ModuleDTO;
import com.khcm.user.service.param.business.system.ModuleParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.ModulePM;
import com.khcm.user.web.admin.model.viewmodel.business.system.ModuleVM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by wangtao on 2018/1/11.
 */
@Mapper
public interface ModuleDTOMapper extends DTOMapper<ModuleDTO, ModuleVM, ModulePM, ModuleParam> {
    ModuleDTOMapper MAPPER = Mappers.getMapper(ModuleDTOMapper.class);
}
