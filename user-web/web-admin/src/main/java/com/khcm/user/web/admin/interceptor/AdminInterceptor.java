package com.khcm.user.web.admin.interceptor;

import com.khcm.user.common.constant.Constants;
import com.khcm.user.common.context.PageContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yangwb on 2017/11/28.
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

    private static final String PAGE_NO = "curr";
    private static final String PAGE_SIZE = "limit";
    private static final String ORDER_BY = "orderBy";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //分页参数
        String pageNoString = request.getParameter(PAGE_NO);
        if (StringUtils.isNotBlank(pageNoString)) {

            Integer pageNo = Integer.valueOf(pageNoString);
            if (pageNo <= 0) {
                pageNo = 1;
            }

            String pageSizeString = request.getParameter(PAGE_SIZE);
            Integer pageSize = Constants.DEFAULT_PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSizeString)) {
                pageSize = Integer.valueOf(pageSizeString);
                if (pageSize <= 0) {
                    pageSize = Constants.DEFAULT_PAGE_SIZE;
                }
            }

            PageContext.setPageNo(pageNo);
            PageContext.setPageSize(pageSize);
        }

        //排序参数
        String orderBy = request.getParameter(ORDER_BY);
        if (StringUtils.isNotBlank(orderBy)) {
            PageContext.setOrderBy(orderBy);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        PageContext.removePageNo();
        PageContext.removePageSize();
        PageContext.removeOrderBy();

    }
}
