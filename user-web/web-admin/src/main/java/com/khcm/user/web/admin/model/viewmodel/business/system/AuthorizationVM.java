package com.khcm.user.web.admin.model.viewmodel.business.system;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wangtao
 * @date 2018/1/11.
 */
@Getter
@Setter
public class AuthorizationVM {
    private Integer id;

    private Integer roleId;

    private Integer appId;

    private Integer resourceId;
}
