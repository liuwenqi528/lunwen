package com.khcm.user.web.admin.model.parammodel.business.system;

import lombok.Getter;
import lombok.Setter;

/**
 * 资源参数对象
 * @author liuwenqi
 * @date 2018-01-30
 */
@Getter
@Setter
public class ResourcePM {

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

    private String beginTime;

    private String endTime;
}
