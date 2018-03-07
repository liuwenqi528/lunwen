package com.khcm.user.web.admin.model.mapper.system;

import com.khcm.user.service.dto.business.system.ResourceDTO;
import com.khcm.user.service.param.business.system.ResourceParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.ResourcePM;
import com.khcm.user.web.admin.model.viewmodel.business.system.ResourceVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Created by wangtao on 2018/1/11.
 */
@Mapper
public interface ResourceDTOMapper extends DTOMapper<ResourceDTO, ResourceVM, ResourcePM, ResourceParam> {

    ResourceDTOMapper MAPPER = Mappers.getMapper(ResourceDTOMapper.class);

    @Override
    @Mapping(target = "beginTime", expression = "java(com.khcm.user.web.admin.utils.MapperUtils.parseToDatetime(resourcePM.getBeginTime()))")
    @Mapping(target = "endTime", expression = "java(com.khcm.user.web.admin.utils.MapperUtils.parseToDatetime(resourcePM.getEndTime()))")
    ResourceParam pmToParam(ResourcePM resourcePM);



}
