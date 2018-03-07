package com.khcm.user.service.impl.system;

import com.khcm.user.dao.entity.business.system.Area;
import com.khcm.user.dao.entity.business.system.Channel;
import com.khcm.user.dao.entity.business.system.QChannel;
import com.khcm.user.dao.entity.business.system.QChannel;
import com.khcm.user.dao.repository.master.system.ChannelRepository;
import com.khcm.user.service.api.system.ChannelService;
import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.system.ChannelDTO;
import com.khcm.user.service.mapper.system.AreaMapper;
import com.khcm.user.service.mapper.system.ChannelMapper;
import com.khcm.user.service.param.business.system.ChannelParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by rqn
 * 渠道服务实现类
 */
@Service("channelService")
@Transactional
@Slf4j
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public ChannelDTO saveOrUpdate(ChannelParam channelParam) {
        if (Objects.isNull(channelParam.getId())) {
            return ChannelMapper.MAPPER.entityToDTO(channelRepository.save(ChannelMapper.MAPPER.paramToEntity(channelParam)));
        } else {
            Channel channel = channelRepository.getOne(channelParam.getId());
            ChannelMapper.MAPPER.paramToEntity(channelParam, channel);
            return ChannelMapper.MAPPER.entityToDTO(channelRepository.save(channel));
        }
    }

    @Override
    public ChannelDTO getById(Integer id) {
        return ChannelMapper.MAPPER.entityToDTO(channelRepository.getOne(id));
    }

    @Override
    public void remove(List<Integer> ids) {
        ids.stream().forEach(id -> channelRepository.delete(id));
    }

    /**
     * update by QimengDuan
     *
     * @param channelParam
     * @return
     * @date 2018-01-25
     */
    @Override
    public PageDTO<ChannelDTO> getPage(ChannelParam channelParam) {
        String name = channelParam.getName();
        QChannel qChannel = QChannel.channel;
        BooleanExpression predicate = qChannel.isNotNull();
        if (StringUtils.isNotBlank(name)) {
            predicate = qChannel.name.like("%" + name + "%");
        }
        Optional<Date> optionalBeginDate = Optional.ofNullable(channelParam.getBeginTime());
        predicate = predicate.and(optionalBeginDate.map(beginDate -> qChannel.gmtCreate.goe(beginDate)).orElse(null));
        Optional<Date> optionalEndDate = Optional.ofNullable(channelParam.getEndTime());
        predicate = predicate.and(optionalEndDate.map(endDate -> qChannel.gmtCreate.loe(endDate)).orElse(null));

        Page<Channel> page = channelRepository.findPage(predicate);
        List<ChannelDTO> channelList = ChannelMapper.MAPPER.entityToDTO(page.getContent());

        return PageDTO.of(page.getTotalElements(), channelList);
    }

    @Override
    public ChannelDTO getOne(ChannelParam channelParam) {
        QChannel qChannel = QChannel.channel;
        BooleanExpression predicate;
        if (StringUtils.isNotBlank(channelParam.getName())) {
            predicate = qChannel.name.eq(channelParam.getName());
        } else {
            predicate = qChannel.isNotNull();
        }
        if (Objects.nonNull(channelParam.getId())) {
            predicate = predicate.and(qChannel.id.ne(channelParam.getId()));
        }
        Channel channel = Optional.ofNullable(channelRepository.findList(predicate)).filter(channels -> channels.size() > 0).map(channels -> channels.get(0)).orElse(null);
        return ChannelMapper.MAPPER.entityToDTO(channel);
    }
}
