package com.khcm.user.web.admin.model.parammodel.business.log;

import com.khcm.user.web.admin.model.parammodel.base.BasePM;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogPM extends BasePM{
    //日志创建日期
    private String gmtCreate;
    //用户IP
    private String ip;
    //日志类型
    private String logType;

    private String appId;
    
    private String username;

    private String beginTime;

    private String endTime;

}
