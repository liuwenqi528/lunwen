package com.khcm.user.service.param.business.system;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by yangwb on 2017/12/6.
 */
@Getter
@Setter
public class AuthorizationParam {

    private  Integer id;

    private Integer roleId;

    private Integer appId;

    private Integer resourceId;

    private List<Integer> resourceIds;
}
