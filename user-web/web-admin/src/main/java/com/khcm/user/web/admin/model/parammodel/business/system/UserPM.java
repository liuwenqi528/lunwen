package com.khcm.user.web.admin.model.parammodel.business.system;

import com.khcm.user.common.enums.UserStatus;
import com.khcm.user.web.admin.model.parammodel.base.BasePM;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户参数 Model 类
 *
 * @author wangtao
 * @date 2017/11/25.
 */
@Getter
@Setter
public class UserPM extends BasePM implements Serializable {

    private String username;

    private Integer id;

    private String password;

    private String email;

    private String phone;

    private Boolean admin;

    private String nickname;

    private String realname;
    private Integer age;
    private String sex;

    private String birthday;

    private String idcard;

    private Integer areaId;

    private String beginTime;

    private String endTime;

    private String channelName;

    private UserStatus status;


}
