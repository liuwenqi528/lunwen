package com.khcm.user.web.admin.model.viewmodel.business.log;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogVM {
    //日志创建日期
    private String gmtCreate;
    //用户IP
    private String ip;
    //日志类型编码
    private String logType;

    private Integer appId;
    //登陆用户名
    private String username;
    //日志类型
    private String title;
    //日志ID
    private Integer id;
    //日志内容
    private String content;
    //开始时间
    private String beginTime;
    //结束时间
    private String endTime;
}
