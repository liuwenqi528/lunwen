package com.khcm.user.web.admin.model.parammodel.business.system;

import com.khcm.user.web.admin.model.parammodel.base.BasePM;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
/**
 * Created by rqn
 */
@Setter
@Getter
public class ChangePwdPM extends BasePM implements Serializable {
    private Integer userId;
    private String password;
    private String newPwd;
    private String confirmPwd;
}
