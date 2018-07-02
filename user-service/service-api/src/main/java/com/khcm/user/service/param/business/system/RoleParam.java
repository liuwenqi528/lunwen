package com.khcm.user.service.param.business.system;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by yangwb on 2017/11/28.
 */
@Getter
@Setter
public class RoleParam {

    private Integer id;

    private String name;

    private String description;

    private Date beginTime;

    private Date endTime;
}
