package com.khcm.user.service.dto.business.system;

import com.khcm.user.common.enums.AreaType;
import com.khcm.user.service.dto.base.TreeDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by rqn on 2018/1/12.
 */
@Getter
@Setter
public class AreaDTO extends TreeDTO<AreaDTO> implements Serializable {
    private String name;
    private String code;
    private String description;
    private String parentName;
    private AreaType areaType;

    @Override
    public String toString() {
        return "AreaDTO{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", parentName='" + parentName + '\'' +
                ", areaType=" + areaType +
                '}';
    }
}
