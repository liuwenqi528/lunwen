package com.khcm.user.web.admin.dialect;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

/**
 * Created by yangwb on 2018/1/23.
 */
public class ShiroExpression extends AbstractDialect implements IExpressionObjectDialect {

    private static final IExpressionObjectFactory EXPRESSION_OBJECTS_FACTORY = new ShiroExpressionObjectFactory();

    private static final String NAME = "ShiroExpression";

    public ShiroExpression() {
        super(NAME);
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return EXPRESSION_OBJECTS_FACTORY;
    }

}
