package com.khcm.user.web.rest.shiro;

import io.buji.pac4j.realm.Pac4jRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

public class RestCasRealm extends Pac4jRealm {

    /**
     * 认证
     * AuthenticationInfo 用来验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return super.doGetAuthenticationInfo(authenticationToken);
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return super.doGetAuthorizationInfo(principals);
    }

}
