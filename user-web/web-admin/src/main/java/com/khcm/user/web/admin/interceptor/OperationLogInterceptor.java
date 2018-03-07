package com.khcm.user.web.admin.interceptor;

import com.khcm.user.service.api.log.LogService;
import com.khcm.user.web.admin.annotation.OperationLog;
import com.khcm.user.web.admin.config.AdminConfig;
import com.khcm.user.web.admin.utils.ShiroUtils;
import com.khcm.user.web.common.utils.SecurityUtils;
import com.khcm.user.web.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 操作日志拦截器
 * Created by yangwb on 2018/1/9.
 */
@Slf4j

public class OperationLogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private LogService logService;

    @Autowired
    private AdminConfig adminConfig;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            if (handlerMethod.getMethod().isAnnotationPresent(OperationLog.class)) {
                //操作名称
                OperationLog operationLog = handlerMethod.getMethod().getDeclaredAnnotation(OperationLog.class);
                String operationName = operationLog.name();

                logService.createOperationLog(ShiroUtils.getCurrentUserId(),
                        adminConfig.getAppCode(),
                        operationName,
                        "请求地址:" + request.getRequestURI(),
                        WebUtils.getIpAddr(request));
            }
        }

    }


}
