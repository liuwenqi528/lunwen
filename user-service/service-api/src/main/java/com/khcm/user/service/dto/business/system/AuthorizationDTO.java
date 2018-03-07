package com.khcm.user.service.dto.business.system;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by yangwb on 2017/12/4.
 */
@Getter
@Setter
public class AuthorizationDTO implements Serializable {
    private Integer id;

    private Integer roleId;

    private Integer appId;

    private Integer resourceId;

    @Override
    public String toString() {
        return "AuthorizationDTO{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", appId=" + appId +
                ", resourceId=" + resourceId +
                '}';
    }
}
