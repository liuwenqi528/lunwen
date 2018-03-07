package com.khcm.user.common.exception;

import com.khcm.user.common.errorcode.PredefinedRetCode;
import com.khcm.user.common.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by yangwb on 2017/11/22.
 */
public class StateException extends AbstractException {

    public static final String ERROR_KEY = "global.error.state";

    public StateException(String errorInfo) {
        super(PredefinedRetCode.STATE_ERROR.getRetCode(), StringUtils.isBlank(errorInfo) ? SpringUtils.getMessage(ERROR_KEY) : errorInfo);
    }

    public StateException(String errorInfo, Throwable throwable) {
        super(PredefinedRetCode.STATE_ERROR.getRetCode(), StringUtils.isBlank(errorInfo) ? SpringUtils.getMessage(ERROR_KEY) : errorInfo, throwable);
    }

}
