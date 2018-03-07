package com.khcm.user.common.ensure;

/**
 *
 */
public class IntegerParam extends ParamBase<Integer> {

    public IntegerParam(Integer param) {
        super(param);
    }

    public ParamBase<Integer> isGt(int operand) {
        result = param > operand;
        return this;
    }

    public ParamBase<Integer> isGte(int operand) {
        result = param >= operand;
        return this;
    }

    public ParamBase<Integer> equalWith(int operand) {
        result = param == operand;
        return this;
    }

    public ParamBase<Integer> isLt(int operand) {
        result = param < operand;
        return this;
    }

    public ParamBase<Integer> isLte(int operand) {
        result = param <= operand;
        return this;
    }

    public ParamBase<Integer> isInRange(int min, int max) {
        result = min <= param && param <= max;
        return this;
    }
}
