package com.khcm.user.common.errorcode;

/**
 * Created by yangwb on 2017/11/22.
 */
public enum PredefinedRetCode {

    //成功
    SUCCESS("0000", "成功"),

    //错误
    SECURITY_ERROR("6014", "安全错误"),
    ARGUMENT_ERROR("6015", "参数错误"),
    STATE_ERROR("6016", "状态错误"),
    SYSTEM_ERROR("6019", "系统错误");

    private String retCode;
    private String defaultRetInfo;

    PredefinedRetCode(String retCode, String defaultRetInfo) {
        this.retCode = retCode;
        this.defaultRetInfo = defaultRetInfo;
    }

    public String getRetCode() {
        return retCode;
    }

    public String getDefaultRetInfo() {
        return defaultRetInfo;
    }
}
