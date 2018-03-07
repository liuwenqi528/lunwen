package com.khcm.user.common.ensure;

/**
 *
 */
public class ReferenceParam extends ParamBase<Object> {
    public ReferenceParam(Object param) {
        super(param);
    }

    public ParamBase<Object> isNotNull() throws IllegalArgumentException {
        result = param != null;
        return this;
    }

    public ParamBase<Object> isNull() {
        result = param == null;
        return this;
    }

    public ParamBase<Object> isInstanceOf(Class<?> classType) throws IllegalArgumentException {
        result = classType.isAssignableFrom(param.getClass());
        return this;
    }
}
