package com.khcm.user.service.api.system;

import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.system.ChannelDTO;
import com.khcm.user.service.param.business.system.ChannelParam;

import java.util.List;

/**
 * 渠道服务
 * @author rqn
 */
public interface ChannelService {

    ChannelDTO saveOrUpdate(ChannelParam channelParam);

    ChannelDTO getById(Integer id);

    void remove(List<Integer> ids);

    PageDTO<ChannelDTO> getPage(ChannelParam channelParam);

    ChannelDTO getOne(ChannelParam channelParam);
}
