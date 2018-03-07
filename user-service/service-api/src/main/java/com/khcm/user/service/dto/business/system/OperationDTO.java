package com.khcm.user.service.dto.business.system;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by yangwb on 2018/1/4.
 */
@Getter
@Setter
public class OperationDTO implements Serializable {

    private String id;
    private String text;
    private String url;
    private String icon;
    private String code;
    private String description;

    @Override
    public String toString() {
        return "OperationDTO{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
