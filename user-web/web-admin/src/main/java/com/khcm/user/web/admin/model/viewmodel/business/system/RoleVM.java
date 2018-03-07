package com.khcm.user.web.admin.model.viewmodel.business.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.khcm.user.common.utils.DateUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by yangwb on 2017/11/28.
 */
@Getter
@Setter
@ToString(exclude ="authorizations")
public class RoleVM {
    private Integer id;

    private String name;

    private Integer priority;

    private String description;

    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtCreate;

    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtModified;

    private List<Integer> userIds;

    private Set<AuthorizationVM> authorizations;
//    private List<Integer> authorizationIds;

}
