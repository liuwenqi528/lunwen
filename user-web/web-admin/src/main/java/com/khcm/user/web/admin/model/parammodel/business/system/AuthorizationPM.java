package com.khcm.user.web.admin.model.parammodel.business.system;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author wangtao
 * @date 2018/1/11.
 */
@Setter
@Getter
//@AllArgsConstructor
//@NoArgsConstructor
public class AuthorizationPM {
    private Integer id;
    private Integer roleId;

    private Integer appId;

    private Integer resourceId;

    private String resourceIds;
}
