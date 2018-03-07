package com.khcm.user.web.admin.utils;

import com.khcm.user.common.constant.Constants;
import com.khcm.user.common.errorcode.PredefinedRetCode;
import com.khcm.user.common.utils.SpringUtils;
import com.khcm.user.web.admin.model.viewmodel.base.ListVM;
import com.khcm.user.web.admin.model.viewmodel.base.PageVM;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.admin.model.viewmodel.base.TreeVM;

import java.util.List;

/**
 * 视图结果工具类
 */
public class VMUtils {

    public static ResultVM resultSuccess() {
        return ResultVM.of(PredefinedRetCode.SUCCESS.getRetCode(), SpringUtils.getMessage(Constants.GLOBAL_SUCCESS));
    }

    public static ResultVM resultSuccess(String key, String... params) {
        return ResultVM.of(PredefinedRetCode.SUCCESS.getRetCode(), SpringUtils.getMessage(key, params));
    }

    public static ResultVM resultFailure() {
        return ResultVM.of(PredefinedRetCode.SYSTEM_ERROR.getRetCode(), SpringUtils.getMessage(Constants.GLOBAL_FAILURE));
    }


    public static <VM> ListVM<VM> listSuccess(List<VM> vmList) {
        ListVM listVM = ListVM.of(PredefinedRetCode.SUCCESS.getRetCode(), SpringUtils.getMessage(Constants.GLOBAL_SUCCESS));
        listVM.setList(vmList);
        return listVM;
    }

    public static <VM> PageVM<VM> pageSuccess(long total, List<VM> rows) {
        PageVM pageVM = PageVM.of(PredefinedRetCode.SUCCESS.getRetCode(), SpringUtils.getMessage(Constants.GLOBAL_SUCCESS));
        pageVM.setTotal(total);
        pageVM.setRows(rows);
        return pageVM;
    }

    public static <VM extends TreeVM> TreeVM<VM> treeSuccess(List<VM> content) {
        TreeVM<VM> treeVM = new TreeVM<>();
        treeVM.setChildren(content);
        return treeVM;
    }
}
