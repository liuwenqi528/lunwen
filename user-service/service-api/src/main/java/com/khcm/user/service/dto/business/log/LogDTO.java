package com.khcm.user.service.dto.business.log;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class LogDTO implements Serializable {
    //日志创建日期
    private Date gmtCreate;
    //用户IP
    private String ip;
    //日志类型编码
    private String logType;
    //日志类型
    private String title;
    //登陆用户名
    private String username;

    private Integer appId;
    //日志ID
    private Integer id;
    //日志内容
    private String content;

    @Override
    public String toString() {
        return "LogDTO{" +
                "gmtCreate=" + gmtCreate +
                ", ip='" + ip + '\'' +
                ", logType='" + logType + '\'' +
                ", title='" + title + '\'' +
                ", username='" + username + '\'' +
                ", appId=" + appId +
                ", id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
