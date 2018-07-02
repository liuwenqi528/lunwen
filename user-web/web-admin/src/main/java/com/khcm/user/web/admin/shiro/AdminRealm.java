package com.khcm.user.web.admin.shiro;

import com.khcm.user.common.enums.UserStatus;
import com.khcm.user.service.api.system.AuthorizationService;
import com.khcm.user.service.api.system.UserService;
import com.khcm.user.service.dto.business.system.LoginDTO;
import com.khcm.user.web.admin.config.AdminConfig;
import com.khcm.user.web.common.shiro.RequiredUsernameException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Set;

/**
 * 自定义 Realm
 *
 * @date 2017/8/24
 */
@EnableConfigurationProperties(AdminConfig.class)
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private AdminConfig adminConfig;

    /**
     * 认证
     * AuthenticationInfo 用来验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        if (StringUtils.isBlank(token.getUsername())) {
            throw new RequiredUsernameException();
        }

        LoginDTO loginDTO = userService.getByUsername(token.getUsername());
        if (loginDTO == null) {
            throw new UnknownAccountException();
        }

        if (UserStatus.isLocked(loginDTO.getStatus())) {
            throw new LockedAccountException();
        }

        if (UserStatus.isDisabled(loginDTO.getStatus())) {
            throw new DisabledAccountException();
        }

        if(!UserStatus.isNormal(loginDTO.getStatus())) {
            throw new AccountException();
        }

        // 认证信息
        Principal user = Principal.of(loginDTO.getId(), loginDTO.getUsername());
        user.setAdmin(loginDTO.getAdmin());
        return new SimpleAuthenticationInfo(user, loginDTO.getPassword(), ByteSource.Util.bytes(loginDTO.getUsername()), getName());
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
