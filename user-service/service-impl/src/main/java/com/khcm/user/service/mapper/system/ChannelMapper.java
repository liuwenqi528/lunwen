package com.khcm.user.service.mapper.system;

import com.khcm.user.dao.entity.business.system.Channel;
import com.khcm.user.service.dto.business.system.ChannelDTO;
import com.khcm.user.service.mapper.EntityMapper;
import com.khcm.user.service.param.business.system.ChannelParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * Created by rqn
 */
@Mapper
public interface ChannelMapper extends EntityMapper<Channel, ChannelDTO, ChannelParam> {
    ChannelMapper MAPPER = Mappers.getMapper(ChannelMapper.class);
}
