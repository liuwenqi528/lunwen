package com.khcm.user.service.dto.business.organization;

import com.khcm.user.service.api.organization.OrganizationService;
import com.khcm.user.service.dto.base.TreeDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author wangtao
 * @date 2017/11/18.
 */
@Getter
@Setter
public class DepartmentDTO extends TreeDTO<DepartmentDTO> implements Serializable {
    private String fullName;

    private String description;

    private Integer organizationId;

    @Override
    public String toString() {
        return "DepartmentDTO{" +
                "fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                ", organizationId=" + organizationId +
                ", organizationName='" + organizationName + '\'' +
                '}';
    }

    private String organizationName;
}
