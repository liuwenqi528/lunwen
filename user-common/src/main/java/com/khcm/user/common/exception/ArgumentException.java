package com.khcm.user.common.exception;

import com.khcm.user.common.errorcode.PredefinedRetCode;
import com.khcm.user.common.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 业务异常的封装
 *
 * @author wangtao
 * @date 2017/8/15
 */
public class ArgumentException extends AbstractException {

    public static final String ERROR_KEY = "global.error.argument";

    public ArgumentException(String errorInfo) {
        super(PredefinedRetCode.ARGUMENT_ERROR.getRetCode(), StringUtils.isBlank(errorInfo) ? SpringUtils.getMessage(ERROR_KEY) : errorInfo);
    }

    public ArgumentException(String errorInfo, Throwable throwable) {
        super(PredefinedRetCode.ARGUMENT_ERROR.getRetCode(), StringUtils.isBlank(errorInfo) ? SpringUtils.getMessage(ERROR_KEY) : errorInfo, throwable);
    }
}
