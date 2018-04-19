package com.khcm.user.web.admin.model.parammodel.business.system;

import com.khcm.user.web.admin.model.parammodel.base.BasePM;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangwb on 2017/11/28.
 */
@Getter
@Setter
public class RolePM  extends BasePM{

    private Integer id;

    private String name;

    private String description;

    private String beginTime;

    private String endTime;
}

