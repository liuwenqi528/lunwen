package com.khcm.user.web.admin.shiro;

import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;

public class AdminPermissionAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor {

    public AdminPermissionAnnotationMethodInterceptor(AnnotationResolver resolver) {
        super(new AdminPermissionAnnotationHandler(), resolver);
    }

}
