package com.khcm.user.service.api.log;

import com.khcm.user.service.dto.base.PageDTO;
import com.khcm.user.service.dto.business.log.LogDTO;
import com.khcm.user.service.param.business.log.LogParam;

import java.util.List;

public interface LogService {

    void createLoginSuccessLog(String username, String appCode, String content, String ip);

    void createLoginFailureLog(String username, String appCode, String content, String ip);

    void createOperationLog(Integer userId, String appCode, String title, String content, String ip);

    List<LogDTO> getSuccessLogList(LogParam logParam);

    List<LogDTO> getFailureLogList();

    List<LogDTO> getOperationLogList();

    PageDTO<LogDTO> getPage(LogParam logParam);

    void remove(List<Integer> ids);
}
