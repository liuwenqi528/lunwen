package com.khcm.user.service.dto.business.system;

import com.khcm.user.common.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户信息 DTO
 *
 * @author wangtao
 * @date 2017/10/25
 */
@Getter
@Setter
@ToString
public class UserDTO implements Serializable {

    private Integer id;

    private Date gmtCreate;

    private Date gmtModified;

    private String username;

    private String email;

    private String phone;

    private String password;

    private String salt;

    private String avatar;

    private Boolean admin;

    private UserStatus status;

    private Date loginTime;

    private String loginIp;

    private Integer loginCount;

    private Date errorTime;

    private String errorIp;

    private Integer errorCount;

    private String nickname;

    private String realname;

    private Date birthday;
    private Integer age;
    private String sex;

    private String idcard;

    private List<Integer> roleIds;

}
