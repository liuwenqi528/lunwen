package com.khcm.user.web.admin.model.mapper.log;

import com.khcm.user.common.utils.DateUtils;
import com.khcm.user.service.dto.business.log.LogDTO;
import com.khcm.user.service.param.business.log.LogParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.log.LogPM;
import com.khcm.user.web.admin.model.viewmodel.business.log.LogVM;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Date;

@Mapper
public interface LogDTOMapper extends DTOMapper<LogDTO, LogVM, LogPM, LogParam> {
    LogDTOMapper MAPPER = Mappers.getMapper(LogDTOMapper.class);
    @Override
    @Mapping(target = "beginTime", expression = "java(parseToDate(logPM.getBeginTime()))")
    @Mapping(target = "endTime", expression = "java(parseToDate(logPM.getEndTime()))")
    LogParam pmToParam(LogPM logPM);

    @Override
    LogVM dtoToVM(LogDTO logDTO);
    default Date parseToDate(String date){
        Date newDate = null;
       if(StringUtils.isNotBlank(date)){
           newDate = DateUtils.getDateTimeFormat(date);
       }
        return newDate;
    }
}
