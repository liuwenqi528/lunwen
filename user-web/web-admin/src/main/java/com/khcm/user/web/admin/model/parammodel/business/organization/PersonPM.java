package com.khcm.user.web.admin.model.parammodel.business.organization;

import com.khcm.user.web.admin.model.parammodel.base.BasePM;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangtao
 * @date 2018/1/11.
 */
@Setter
@Getter
public class PersonPM extends BasePM {
    private Integer id;
    private String name;

    private String sex;

    private Integer age;

    private Integer organizationId;

    private Integer departmentId;
    private Integer userId;
}
