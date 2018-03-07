package com.khcm.user.common.ensure;

/**
 *
 */
public final class Ensure {
    private Ensure() {
    }

    public static ReferenceParam that(Object refObj) {
        return new ReferenceParam(refObj);
    }

    public static IntegerParam that(int num) {
        return new IntegerParam(num);
    }

    public static StringParam that(String text) {
        return new StringParam(text);
    }

    public static BooleanParam that(boolean boolValue) {
        return new BooleanParam(boolValue);
    }

    public static EnumParam that(Enum<?> enumValue) {
        return new EnumParam(enumValue);
    }
}
