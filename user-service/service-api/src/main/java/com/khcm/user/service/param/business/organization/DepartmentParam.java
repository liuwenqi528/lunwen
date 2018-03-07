package com.khcm.user.service.param.business.organization;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by
 * @author  yangwb on 2017/12/6.
 */
@Getter
@Setter
@ToString
public class DepartmentParam {

    private Integer id;

    private String fullName;

    private String description;

    private String name;

    private Integer parentId;

    private Integer organizationId;

    private List<Integer> organizationIds;

    private Date beginTime;

    private Date endTime;

}
