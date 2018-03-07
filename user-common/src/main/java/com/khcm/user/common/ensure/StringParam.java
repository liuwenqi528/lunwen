package com.khcm.user.common.ensure;

import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public class StringParam extends ParamBase<String> {
    public StringParam(String param) {
        super(param);
    }

    public ParamBase<String> equalWith(String operand) {
        result = StringUtils.equals(param, operand);
        return this;
    }

    public ParamBase<String> isNotNullOrEmpty() {
        result = StringUtils.isNotBlank(param);
        return this;
    }

    public ParamBase<String> isNotNull() {
        result = param != null;
        return this;
    }

    public ParamBase<String> hasLengthBetween(int minLength, int maxLength) {
        result = minLength <= param.length() && param.length() <= maxLength;
        return this;
    }

    public ParamBase<String> isInteger() {
        try {
            Integer.parseInt(param);
            result = true;
        } catch (Exception exp) {
            result = false;
        }
        return this;
    }

}
