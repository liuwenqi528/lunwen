package com.khcm.user.service.param.business.system;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author rqn on 2018/2/1.
 */
@Getter
@Setter
public class UserInfoParam {
    private Integer id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private String realname;
    private Integer age;
    private String sex;
    private Date birthday;
    private String idcard;
    private Integer areaId;
}
