package com.khcm.user.service.api.system;

import com.khcm.user.service.dto.business.system.ConfigLoginDTO;

public interface ConfigService {

    /**
     * 登录配置
     *
     * @return
     */
    ConfigLoginDTO getLoginConfig();

}
