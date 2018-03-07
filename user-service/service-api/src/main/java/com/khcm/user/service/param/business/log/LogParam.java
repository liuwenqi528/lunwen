package com.khcm.user.service.param.business.log;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LogParam {
    //日志创建日期
    private String gmtCreate;
    //用户IP
    private String ip;
    //日志类型
    private String logType;

    private Integer appId;

    private String username;

    private Date beginTime;

    private Date endTime;

}
