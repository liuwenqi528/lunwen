package com.khcm.user.web.admin.exception;

import com.khcm.user.common.errorcode.PredefinedRetCode;
import com.khcm.user.common.exception.AbstractException;
import com.khcm.user.common.utils.SpringUtils;
import com.khcm.user.web.admin.model.viewmodel.base.ResultVM;
import com.khcm.user.web.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by
 * @author yangwb on 2017/11/29.
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(AbstractException.class)
    public ResultVM handleException(HttpServletRequest request, HttpServletResponse response, AbstractException e) {
        log.error("系统异常：" + e.getMessage(), e);
        if (WebUtils.isAjaxRequest(request)) {
            response.setHeader("systemStatus", "0");
            return ResultVM.of(e.getErrorCode(), SpringUtils.getMessage("global.page.error"));
        }

        try {
            response.sendRedirect(request.getContextPath() + "/pageError");
        } catch (IOException e1) {
            log.error("系统异常：" + e.getMessage(), e);
        }
        return null;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResultVM handleException(HttpServletRequest request, HttpServletResponse response, UnauthorizedException e){
        log.error("安全异常：" + e.getMessage(), e);
        if (WebUtils.isAjaxRequest(request)) {
            response.setHeader("systemStatus", "0");
            return ResultVM.of(PredefinedRetCode.SECURITY_ERROR.getRetCode(), SpringUtils.getMessage("global.page.unauth"));
        }

        try {
            response.sendRedirect(request.getContextPath() + "/pageUnauth");
        } catch (IOException e1) {
            log.error("安全异常：" + e.getMessage(), e);
        }
        return null;
    }

    @ExceptionHandler(Exception.class)
    public ResultVM handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("未知异常：" + e.getMessage(), e);

        if (WebUtils.isAjaxRequest(request)) {
            response.setHeader("systemStatus", "0");
            return ResultVM.of(PredefinedRetCode.SYSTEM_ERROR.getRetCode(), SpringUtils.getMessage("global.page.notfound"));
        }

        try {
            response.sendRedirect(request.getContextPath() + "/pageError");
        } catch (IOException e1) {
            log.error("未知异常：" + e.getMessage(), e);
        }
        return null;
    }

}
