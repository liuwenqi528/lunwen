package com.khcm.user.web.admin.model.parammodel.business.organization;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangwb on 2017/12/4.
 */
@Getter
@Setter
public class OrganizationPM {
    private Integer id;
    private Integer parentId;
    private String name;
    private String fullName;
    private String description;

}
