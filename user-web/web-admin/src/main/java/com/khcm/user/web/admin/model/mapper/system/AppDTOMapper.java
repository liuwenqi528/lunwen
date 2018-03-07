package com.khcm.user.web.admin.model.mapper.system;

import com.khcm.user.service.dto.business.system.AppDTO;
import com.khcm.user.service.param.business.system.AppParam;
import com.khcm.user.service.param.business.system.ResourceParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.AppPM;
import com.khcm.user.web.admin.model.parammodel.business.system.ResourcePM;
import com.khcm.user.web.admin.model.viewmodel.business.system.AppVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Created by wangtao on 2018/1/11.
 */
@Mapper
public interface AppDTOMapper extends DTOMapper<AppDTO, AppVM, AppPM, AppParam> {
    AppDTOMapper MAPPER = Mappers.getMapper(AppDTOMapper.class);

    @Override
    @Mapping(target = "beginTime", expression = "java(com.khcm.user.web.admin.utils.MapperUtils.parseToDatetime(appPM.getBeginTime()))")
    @Mapping(target = "endTime", expression = "java(com.khcm.user.web.admin.utils.MapperUtils.parseToDatetime(appPM.getEndTime()))")
    AppParam pmToParam(AppPM appPM);
}
