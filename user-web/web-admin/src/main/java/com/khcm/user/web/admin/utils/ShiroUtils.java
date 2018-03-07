package com.khcm.user.web.admin.utils;


import com.khcm.user.web.admin.shiro.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by yangwb on 2018/1/2.
 */
public class ShiroUtils {

    public static Integer getCurrentUserId() {
        Principal user = getCurrentUser();
        return user == null ? null : user.getUserId();
    }

    public static String getCurrentUsername() {
        Principal user = getCurrentUser();
        return user == null ? null : user.getUsername();
    }

    public static Principal getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        return (Principal) subject.getPrincipal();
    }
}
