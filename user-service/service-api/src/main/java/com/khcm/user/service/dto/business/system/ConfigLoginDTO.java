package com.khcm.user.service.dto.business.system;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@ToString
public class ConfigLoginDTO implements Serializable {

    public static final String LOGIN_SSO_ENABLE = "sso_enable";
    public static final String LOGIN_ERROR_INTERVAL = "login_error_interval";
    public static final String LOGIN_ERROR_TIMES = "login_error_times";
    public static final String LOGIN_LOCKED_ERROR_TIMES = "login_locked_error_times";

    @NonNull
    private Map<String, String> attr;

    public Map<String, String> getAttr() {
        if (attr == null) {
            attr = new HashMap<>();
        }
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public Boolean isSsoEnable() {
        String ssoEnable = getAttr().get(LOGIN_SSO_ENABLE);
        return BooleanUtils.toBoolean(ssoEnable);
    }

    public void setSsoEnable(Boolean ssoEnable) {
        getAttr().put(LOGIN_SSO_ENABLE, BooleanUtils.toStringTrueFalse(ssoEnable));
    }

    public Integer getErrorInterval() {
        String interval = getAttr().get(LOGIN_ERROR_INTERVAL);
        if (NumberUtils.isDigits(interval)) {
            return Integer.parseInt(interval);
        } else {
            // 默认间隔30分钟
            return 30;
        }
    }

    public void setErrorInterval(Integer errorInterval) {
        if (errorInterval != null) {
            getAttr().put(LOGIN_ERROR_INTERVAL, errorInterval.toString());
        } else {
            getAttr().put(LOGIN_ERROR_INTERVAL, null);
        }
    }

    public Integer getErrorTimes() {
        String times = getAttr().get(LOGIN_ERROR_TIMES);
        if (NumberUtils.isDigits(times)) {
            return Integer.parseInt(times);
        } else {
            // 默认3次
            return 3;
        }
    }

    public void setErrorTimes(Integer errorTimes) {
        if (errorTimes != null) {
            getAttr().put(LOGIN_ERROR_TIMES, errorTimes.toString());
        } else {
            getAttr().put(LOGIN_ERROR_TIMES, null);
        }
    }

    public Integer getLockedErrorTimes() {
        String times = getAttr().get(LOGIN_LOCKED_ERROR_TIMES);
        if (NumberUtils.isDigits(times)) {
            return Integer.parseInt(times);
        } else {
            // 默认5次
            return 5;
        }
    }

    public void setLockedErrorTimes(Integer errorTimes) {
        if (errorTimes != null) {
            getAttr().put(LOGIN_LOCKED_ERROR_TIMES, errorTimes.toString());
        } else {
            getAttr().put(LOGIN_LOCKED_ERROR_TIMES, null);
        }
    }
}
