package com.khcm.user.service.dto.business.organization;

import com.khcm.user.service.dto.base.TreeDTO;
import com.khcm.user.service.dto.business.system.ResourceDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

/**
 * @author wangtao
 * @date 2017/11/18.
 */
@Getter
@Setter
public class OrganizationDTO extends TreeDTO<OrganizationDTO> implements Serializable {
    private String fullName;

    private String description;

    @Override
    public String toString() {
        return "OrganizationDTO{" +
                "fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
