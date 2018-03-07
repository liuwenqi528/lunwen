package com.khcm.user.web.admin.dialect;

import com.khcm.user.web.admin.utils.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by yangwb on 2018/1/23.
 */
public class ShiroExpressionObjectFactory implements IExpressionObjectFactory {

    public static final String SHIRO_USER_ID_OBJECT_NAME = "userId";
    public static final String SHIRO_USER_NAME_OBJECT_NAME = "userName";

    public static final Set<String> ALL_EXPRESSION_OBJECT_NAMES;

    static {
        final Set<String> allExpressionObjectNames = new LinkedHashSet<>();
        allExpressionObjectNames.add(SHIRO_USER_ID_OBJECT_NAME);
        allExpressionObjectNames.add(SHIRO_USER_NAME_OBJECT_NAME);
        ALL_EXPRESSION_OBJECT_NAMES = Collections.unmodifiableSet(allExpressionObjectNames);

    }

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return ALL_EXPRESSION_OBJECT_NAMES;
    }

    @Override
    public Object buildObject(IExpressionContext context, String expressionObjectName) {

        if (StringUtils.equals(SHIRO_USER_ID_OBJECT_NAME, expressionObjectName)) {
            return ShiroUtils.getCurrentUserId();
        }

        if (StringUtils.equals(SHIRO_USER_NAME_OBJECT_NAME, expressionObjectName)) {
            return ShiroUtils.getCurrentUsername();
        }

        return null;
    }

    @Override
    public boolean isCacheable(String expressionObjectName) {
        return expressionObjectName != null &&
                (StringUtils.equals(SHIRO_USER_ID_OBJECT_NAME, expressionObjectName)
                        || StringUtils.equals(SHIRO_USER_NAME_OBJECT_NAME, expressionObjectName));
    }
}
