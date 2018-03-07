package com.khcm.user.common.ensure;

/**
 *
 */
public class EnumParam extends ParamBase<Enum> {
    public EnumParam(Enum<?> param) {
        super(param);
    }

    public ParamBase<Enum> equalWith(Enum<?> operand) {
        result = param.equals(operand);
        return this;
    }

    public ParamBase<Enum> isNotNull() {
        result = param != null;
        return this;
    }
}
