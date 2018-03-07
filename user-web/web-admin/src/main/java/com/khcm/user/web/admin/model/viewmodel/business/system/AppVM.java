package com.khcm.user.web.admin.model.viewmodel.business.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.khcm.user.common.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AppVM {

    private Integer id;

    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtCreate;

    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtModified;

    private String name;

    private String code;

    private String url;

    private Integer priority;

    private String description;

}
