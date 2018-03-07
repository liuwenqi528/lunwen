package com.khcm.user.common.enums;

/**
 * 用户状态枚举值
 *
 * @author wangtao
 * @date 2018/1/4
 */
public enum UserStatus {
    /**
     * 正常
     */
    NORMAL,
    /**
     * 禁用
     */
    DISABLED,
    /**
     * 锁定
     */
    LOCKED,
    /**
     * 未激活
     */
    UNACTIVED,
    /**
     * 关闭（删除）
     */
    CLOSED;

    public static boolean isNormal(UserStatus status) {
        return NORMAL == status;
    }

    public static boolean isDisabled(UserStatus status) {
        return DISABLED == status;
    }

    public static boolean isLocked(UserStatus status) {
        return LOCKED == status;
    }

    public static boolean isUnactived(UserStatus status) {
        return UNACTIVED == status;
    }

    public static boolean isClosed(UserStatus status) {
        return CLOSED == status;
    }

}
