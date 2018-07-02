package com.khcm.user.service.impl.system;

import com.khcm.user.common.enums.ConfigType;
import com.khcm.user.dao.entity.business.system.Config;
import com.khcm.user.dao.repository.master.system.ConfigRepository;
import com.khcm.user.service.api.system.ConfigService;
import com.khcm.user.service.dto.business.system.ConfigLoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    /**
     * 获取登录配置
     *
     * @return
     */
    @Override
    public ConfigLoginDTO getLoginConfig() {
        Config config = configRepository.findByType(ConfigType.LOGIN);
        return ConfigLoginDTO.of(config.getItems());
    }

}