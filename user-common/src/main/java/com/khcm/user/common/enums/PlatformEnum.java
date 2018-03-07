package com.khcm.user.common.enums;

/**
 * 设备来源
 * @author wangtao
 * @date 2018/1/24
 */
public enum  PlatformEnum {

    /**
     * ANDROID：安卓；IOS：IOS系统；H5：H5页面；PC：电脑端
     */
    ANDROID,IOS,H5,PC;

    public static boolean isAndroid(PlatformEnum platform) {
        return ANDROID == platform;
    }

    public static boolean isIos(PlatformEnum platform) {
        return IOS == platform;
    }

    public static boolean isH5(PlatformEnum platform) {
        return H5 == platform;
    }

    public static boolean isPc(PlatformEnum platform) {
        return PC == platform;
    }

}
