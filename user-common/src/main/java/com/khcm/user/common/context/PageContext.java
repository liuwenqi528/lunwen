package com.khcm.user.common.context;

import com.khcm.user.common.constant.Constants;
import org.apache.commons.lang3.StringUtils;

/**
 * 分页参数
 * @author  lwq on 2018/1/5.
 */
public class PageContext {

    private static final ThreadLocal<Integer> PAGE_NO = new ThreadLocal<>();
    private static final ThreadLocal<Integer> PAGE_SIZE = new ThreadLocal<>();
    private static final ThreadLocal<String> ORDER_BY = new ThreadLocal<>();

    public static Integer getPageNo() {
        return PAGE_NO.get() == null ? 1 : PAGE_NO.get();
    }

    public static void setPageNo(Integer pageNo) {
        PAGE_NO.set(pageNo);
    }

    public static void removePageNo() {
        PAGE_NO.remove();
    }

    public static Integer getPageSize() {
        return PAGE_SIZE.get() == null ? Constants.DEFAULT_PAGE_SIZE : PAGE_SIZE.get();
    }

    public static void setPageSize(Integer pageSize) {
        PAGE_SIZE.set(pageSize);
    }

    public static void removePageSize() {
        PAGE_SIZE.remove();
    }

    public static String getOrderBy() {
        return StringUtils.trimToEmpty(ORDER_BY.get());
    }

    public static void setOrderBy(String orderBy) {
        ORDER_BY.set(orderBy);
    }

    public static void removeOrderBy() {
        ORDER_BY.remove();
    }

}
