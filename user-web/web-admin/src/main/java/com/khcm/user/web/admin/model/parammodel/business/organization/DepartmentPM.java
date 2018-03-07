package com.khcm.user.web.admin.model.parammodel.business.organization;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by yangwb on 2017/12/4.
 */
@Getter
@Setter
public class DepartmentPM {

    private Integer id;
    private String fullName;

    private String description;

    private Integer organizationId;

    private List<Integer> organizationIds;
    private String name;

    private Integer parentId;

    private String beginTime;

    private String endTime;

}
