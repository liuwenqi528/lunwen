package com.khcm.user.dao.repository.master.system;

import com.khcm.user.common.enums.ConfigType;
import com.khcm.user.dao.entity.business.system.Config;
import com.khcm.user.dao.repository.BaseRepository;

import java.util.List;

/**
 * 系统配置
 * @author wangtao
 * on 2017/12/29
 */
public interface ConfigRepository extends BaseRepository<Config, Integer> {
    /**
     * 根据配置类型查询配置
     * @param type
     * @return
     */
    Config findByType(ConfigType type);

//    Config findByNameIsNotNullAndTypeIsNotNull(Config config);

    void deleteByType(ConfigType type);
}
