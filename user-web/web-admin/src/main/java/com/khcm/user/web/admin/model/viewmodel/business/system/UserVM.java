package com.khcm.user.web.admin.model.viewmodel.business.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.khcm.user.common.enums.UserStatus;
import com.khcm.user.common.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by wangtao on 2017/11/28.
 */
@Getter
@Setter
public class UserVM {
    private Integer id;

    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtCreate;
    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date gmtModified;

    private String username;

    private String password;

    private String email;

    private String phone;

    private Boolean enabled;

    private Boolean admin;

    private UserStatus status;

    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date loginTime;

    private String loginIp;

    private Integer loginCount;

    @JSONField(format = DateUtils.STANDARD_DATETIME)
    private Date errorTime;

    private String errorIp;

    private Integer errorCount;

    private String registerIp;

    private String nickname;

    private String realname;

    private Integer age;

    private String sex;

    @JSONField(format = DateUtils.STANDARD_DATE)
    private Date birthday;

    private String idcard;

    private List<Integer> roleIds;

    private String statusName;
}
