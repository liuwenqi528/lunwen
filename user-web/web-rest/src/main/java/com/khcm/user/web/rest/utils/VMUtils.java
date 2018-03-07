package com.khcm.user.web.rest.utils;

import com.khcm.user.common.constant.Constants;
import com.khcm.user.common.errorcode.PredefinedRetCode;
import com.khcm.user.common.utils.SpringUtils;
import com.khcm.user.web.rest.model.viewmodel.base.ResultVM;

/**
 * Created by yangwb on 2018/3/6.
 */
public class VMUtils {

    public static ResultVM resultSuccess() {
        return ResultVM.of(PredefinedRetCode.SUCCESS.getRetCode(), SpringUtils.getMessage(Constants.GLOBAL_SUCCESS));
    }

}
