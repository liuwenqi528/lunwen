package com.khcm.user.service.dto.business.organization;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工 DTO
 *
 * @author wangtao
 * @date 2017/9/27
 */
@Getter
@Setter
public class PersonDTO implements Serializable {
    private Integer id;
    private String name;

    private String sex;

    private Integer age;

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", organizationId=" + organizationId +
                ", departmentId=" + departmentId +
                ", userId=" + userId +
                ", organizationName='" + organizationName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", userName='" + userName + '\'' +
                ", gmtCreate=" + gmtCreate +
                '}';
    }

    private Integer organizationId;

    private Integer departmentId;
    private Integer userId;

    private String organizationName;
    private String departmentName;
    private String userName;

    private Date gmtCreate;

}
