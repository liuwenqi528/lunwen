package com.khcm.user.service.param.business.system;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by yangwb on 2017/12/6.
 */
@Getter
@Setter
public class AppParam {
    private Integer id;
    private Date beginTime;
    private Date endTime;
    private String code;
    private String name;
    private String url;
    private Integer priority;
    private String description;
}
