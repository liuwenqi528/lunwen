package com.khcm.user.service.param.business.system;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by yangwb on 2017/12/6.
 */
@Getter
@Setter
@Data
public class ResourceParam {
    private Integer id;
    private Integer parentId;
    private String name;
    private String code;
    private String icon;
    private String url;
    private String resourceType;
    private String description;
    private Integer appId;
    private String appCode;
    private Date beginTime;
    private Date endTime;
}
