package com.khcm.user.web.admin.shiro;

import com.khcm.user.service.api.system.AuthorizationService;
import com.khcm.user.web.admin.config.AdminConfig;
import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.token.Pac4jToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Set;

@Slf4j
public class AdminCasRealm extends Pac4jRealm {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private AdminConfig adminConfig;

    /**
     * 认证
     * AuthenticationInfo 用来验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        Pac4jToken token = (Pac4jToken) authenticationToken;
        LinkedHashMap<String, CommonProfile> profiles = token.getProfiles();
        // 获取用户信息
        CommonProfile casProfile = profiles.get("cas");
        Integer userId = Integer.parseInt(casProfile.getAttribute("id").toString());
        Boolean isAdmin = Boolean.parseBoolean(casProfile.getAttribute("is_admin").toString());

        Principal user = Principal.of(userId, profiles.values().iterator().next().getId());
        user.setAdmin(isAdmin);
        PrincipalCollection principalCollection = new SimplePrincipalCollection(user, getName());
        return new SimpleAuthenticationInfo(principalCollection, profiles.hashCode());
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Principal user = (Principal) principals.getPrimaryPrincipal();

        if (user != null) {
            Set<String> permissions = authorizationService.getAppPermsByUserId(user.getUserId(), adminConfig.getAppCode());
            authorizationInfo.addStringPermissions(permissions);
        }

        return authorizationInfo;
    }
}
