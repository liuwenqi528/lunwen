package com.khcm.user.web.admin.model.mapper.system;

import com.khcm.user.service.dto.business.system.ChannelDTO;
import com.khcm.user.service.param.business.system.ChannelParam;
import com.khcm.user.web.admin.model.mapper.DTOMapper;
import com.khcm.user.web.admin.model.parammodel.business.system.ChannelPM;
import com.khcm.user.web.admin.model.viewmodel.business.system.ChannelVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChannelDTOMapper extends DTOMapper<ChannelDTO, ChannelVM, ChannelPM, ChannelParam> {
    ChannelDTOMapper MAPPER = Mappers.getMapper(ChannelDTOMapper.class);


    @Override
    @Mapping(target = "beginTime", expression = "java(com.khcm.user.web.admin.utils.MapperUtils.parseToDatetime(channelPM.getBeginTime()))")
    @Mapping(target = "endTime", expression = "java(com.khcm.user.web.admin.utils.MapperUtils.parseToDatetime(channelPM.getEndTime()))")
    ChannelParam pmToParam(ChannelPM channelPM);
}
