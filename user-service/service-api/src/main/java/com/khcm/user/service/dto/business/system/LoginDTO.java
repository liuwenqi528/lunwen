package com.khcm.user.service.dto.business.system;

import com.khcm.user.common.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class LoginDTO implements Serializable {

    private Integer id;

    private String username;

    private String email;

    private String phone;

    private String password;

    private Boolean admin;

    private UserStatus status;

    private Date loginTime;

    private String loginIp;

    private Integer loginCount;

    private Date errorTime;

    private String errorIp;

    private Integer errorCount;

    private List<Integer> roleIds;

    @Override
    public String toString() {
        return "LoginDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                ", status=" + status +
                ", loginTime=" + loginTime +
                ", loginIp='" + loginIp + '\'' +
                ", loginCount=" + loginCount +
                ", errorTime=" + errorTime +
                ", errorIp='" + errorIp + '\'' +
                ", errorCount=" + errorCount +
                ", roleIds=" + roleIds +
                '}';
    }
}
