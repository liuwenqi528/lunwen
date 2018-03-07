package com.khcm.user.web.admin.model.viewmodel.business.organization;

import com.khcm.user.web.admin.model.viewmodel.base.TreeVM;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by yangwb on 2017/12/4.
 */
@Getter
@Setter
public class DepartmentVM extends TreeVM<DepartmentVM> {
    private String fullName;

    private String description;

    private Integer parentId;

    private Integer organizationId;
    private String organizationName;
}
