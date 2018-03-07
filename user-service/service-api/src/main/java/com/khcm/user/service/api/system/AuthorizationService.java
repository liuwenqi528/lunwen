package com.khcm.user.service.api.system;

import com.khcm.user.service.dto.business.system.ModuleDTO;
import com.khcm.user.service.param.business.system.AuthorizationParam;

import java.util.List;
import java.util.Set;

/**
 * 授权服务
 * Created by yangwb on 2017/12/4.
 */
public interface AuthorizationService {

    List<ModuleDTO> getAppModulesByUserId(Integer userId, String appCode);
    Set<String> getAppPermsByUserId(Integer userId, String appCode);

    void saveOrUpdate(AuthorizationParam authorizationParam);
}
