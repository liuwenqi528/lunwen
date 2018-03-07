package com.khcm.user.web.admin.utils;

import com.khcm.user.common.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by
 *
 * @author: liuwenqi on 2018-01-31.
 * Description:
 */
public class MapperUtils {

    /**
     * 字符串转换成日期类型
     *
     * @param dateParam
     * @return
     */
    public static Date parseToDatetime(String dateParam) {
        if (StringUtils.isNotBlank(dateParam)) {
            return DateUtils.getDateTimeFormat(dateParam);
        }
        return null;
    }
    /**
     * 字符串转换成日期类型
     *
     * @param dateParam
     * @return
     */
    public static Date parseToDate(String dateParam) {
        if (StringUtils.isNotBlank(dateParam)) {
            return DateUtils.getDateFormat(dateParam);
        }
        return null;
    }
}
