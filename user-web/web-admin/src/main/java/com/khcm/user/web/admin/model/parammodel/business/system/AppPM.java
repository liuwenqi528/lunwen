package com.khcm.user.web.admin.model.parammodel.business.system;

import com.khcm.user.web.admin.model.parammodel.base.BasePM;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AppPM  extends BasePM{
    private Integer id;
    private String beginTime;
    private String endTime;
    private String code;
    private String name;
    private String url;
    private Integer priority;
    private String description;

}
