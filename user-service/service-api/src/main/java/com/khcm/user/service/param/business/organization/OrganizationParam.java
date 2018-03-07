package com.khcm.user.service.param.business.organization;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangwb on 2017/12/6.
 */
@Getter
@Setter
@Data
public class OrganizationParam {
    private Integer id;
    private Integer parentId;
    private String fullName;
    private String name;
    private String description;
}
