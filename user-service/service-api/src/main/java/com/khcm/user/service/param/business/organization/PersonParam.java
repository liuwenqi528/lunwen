package com.khcm.user.service.param.business.organization;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by yangwb on 2017/12/6.
 */
@Getter
@Setter
public class PersonParam {
    private Integer id;

    private String name;

    private String sex;

    private Integer age;

    private Integer organizationId;

    private Integer departmentId;
    private Integer userId;
}
