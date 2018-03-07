package com.khcm.user.web.admin.model.viewmodel.business.organization;

import com.khcm.user.web.admin.model.viewmodel.base.TreeVM;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Created by yangwb on 2017/12/4.
 */
@Getter
@Setter
public class OrganizationVM extends TreeVM<OrganizationVM> {
    private String fullName;

    private String description;

}
