package com.khcm.user.common.enums;

/**
 * @author wangtao
 * @date 2018/1/12
 */
public enum PersonStatus {

    /**
     * NORMAL：正常；LOCKED：锁定；CLOSED：关闭
     */
    NORMAL, LOCKED, CLOSED;

    public boolean isNormal(PersonStatus status) {
        return status == NORMAL;
    }

    public boolean isLocked(PersonStatus status) {
        return status == LOCKED;
    }

    public boolean isClosed(PersonStatus status) {
        return status == CLOSED;
    }
}
