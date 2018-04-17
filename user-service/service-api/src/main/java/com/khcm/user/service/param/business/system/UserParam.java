package com.khcm.user.service.param.business.system;

import com.khcm.user.common.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by yangwb on 2017/11/28.
 */
@Getter
@Setter
public class UserParam {

    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String phone;
    private Boolean admin;
    private String realname;
    private Date beginTime;
    private Date endTime;
    private UserStatus status;
    private String registerIp;

}
