package com.khcm.user.service.impl.system;

import com.khcm.user.common.enums.ConfigType;
import com.khcm.user.dao.entity.business.system.Config;
import com.khcm.user.dao.entity.business.system.QConfig;
import com.khcm.user.dao.repository.master.system.ConfigRepository;
import com.khcm.user.service.api.system.ConfigService;
import com.khcm.user.service.dto.business.system.ConfigDTO;
import com.khcm.user.service.dto.business.system.ConfigLoginDTO;
import com.khcm.user.service.dto.business.system.ConfigTabDTO;
import com.khcm.user.service.mapper.system.ConfigMapper;
import com.khcm.user.service.mapper.system.ConfigTabMapper;
import com.khcm.user.service.param.business.system.ConfigParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@CacheConfig(cacheNames = "user")
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    /**
     * 获取所有配置
     *
     * @return
     */
    @Override
    public List<ConfigTabDTO> getList() {
        Sort sort = new Sort(Sort.Direction.ASC, "sort");
        List<Config> configs = configRepository.findAll(sort);
        return ConfigTabMapper.MAPPER.entityToDTO(configs);
    }

    /**
     * 获取登录配置
     *
     * @return
     */
    @Override
    @Cacheable
    public ConfigLoginDTO getLoginConfig() {
        Config config = configRepository.findByType(ConfigType.LOGIN);
        return ConfigLoginDTO.of(config.getItems());
    }

    /**
     * 保存配置信息
     *
     * @param configParam
     * @return
     */
    @Override
    public ConfigDTO saveOrUpdate(ConfigParam configParam) {
        //根据保存的对象的type,获取详细的配置信息
        Optional<ConfigParam> configParamOptional = Optional.ofNullable(configParam);
        //将此配置清空
        Config config = configParamOptional.map(configParams1 -> configParams1.getType()).map(type -> configRepository.findByType(Enum.valueOf(ConfigType.class, type))).orElse(null);

        Optional<Config> configOptional = Optional.ofNullable(config);
        configOptional.ifPresent(config1-> {
            config.setItems(configParam.getItems());
        });
        //保存新的配置
        return ConfigMapper.MAPPER.entityToDTO(configRepository.save(config));
    }

    /**
     * 根据配置类型获取数据
     *
     * @param type
     * @return
     */
    @Override
    public ConfigDTO getListByType(String type) {
        QConfig qConfig = QConfig.config;
        BooleanExpression booleanExpression = qConfig.isNotNull();
        booleanExpression.and(qConfig.type.eq(ConfigType.valueOf(type)));
        Config config = configRepository.findByType(ConfigType.valueOf(type));
        return ConfigMapper.MAPPER.entityToDTO(config);
    }
}