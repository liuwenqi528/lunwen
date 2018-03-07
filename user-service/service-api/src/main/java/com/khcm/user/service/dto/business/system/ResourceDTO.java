package com.khcm.user.service.dto.business.system;

import com.khcm.user.service.dto.base.TreeDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ResourceDTO extends TreeDTO<ResourceDTO> implements Serializable {

    private String url;
    private String icon;
    private String code;
    private String resourceType;
    private String description;

    @Override
    public String toString() {
        return "ResourceDTO{" +
                "url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", code='" + code + '\'' +
                ", resourceType='" + resourceType + '\'' +
                ", description='" + description + '\'' +
                ", appId=" + appId +
                '}';
    }

    private Integer appId;


}
