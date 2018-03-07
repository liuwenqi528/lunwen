package com.khcm.user.service.api.system;

import com.khcm.user.service.dto.business.system.ConfigDTO;
import com.khcm.user.service.dto.business.system.ConfigLoginDTO;
import com.khcm.user.service.dto.business.system.ConfigTabDTO;
import com.khcm.user.service.param.business.system.ConfigParam;

import java.util.List;

public interface ConfigService {

    /**
     * 获取所有配置
     *
     * @return
     */
    List<ConfigTabDTO> getList();

    /**
     * 登录配置
     *
     * @return
     */
    ConfigLoginDTO getLoginConfig();

    /**
     * 保存配置信息
     *
     * @param configParam
     * @return
     */
    ConfigDTO saveOrUpdate(ConfigParam configParam);

    /**
     * 根据配置类型获取数据
     *
     * @param type
     * @return
     */
    ConfigDTO getListByType(String type);
}
