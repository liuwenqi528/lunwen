/**
 * Created by IntelliJ IDEA.
 * User: LiuWenqi
 * Date: 2018-01-17
 * Time: 16:44
 * Description:
 */
package com.khcm.user.web.admin.model.parammodel.base;

import com.khcm.user.common.context.PageContext;

public class BasePM {
    private Integer page;
    private Integer rows;
    private String orderBy;

    public void setPage(Integer page) {
        PageContext.setPageNo(page);
    }

    public void setRows(Integer rows) {
        PageContext.setPageSize(rows);
    }

    public void setOrderBy(String orderBy) {
        PageContext.setOrderBy(orderBy);
    }

}
