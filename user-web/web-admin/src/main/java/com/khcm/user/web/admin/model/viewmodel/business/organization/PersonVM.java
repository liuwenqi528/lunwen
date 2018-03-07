package com.khcm.user.web.admin.model.viewmodel.business.organization;

import com.alibaba.fastjson.annotation.JSONField;
import com.khcm.user.common.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author wangtao
 * @date 2018/1/11.
 */
@Setter
@Getter
public class PersonVM {
    private Integer id;
    private String name;

    private String sex;

    private Integer age;

    private Integer organizationId;

    private Integer departmentId;
    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtCreate;

    private String organizationName;
    private String departmentName;
    private Integer userId;
    private String userName;

}
