package com.khcm.user.web.admin.dialect.tag;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * 管理标签
 * Created by yangwb on 2017/12/29.
 */
public class AdminTagProcessor extends AbstractElementTagProcessor {

    private static final String TAG_NAME = "perm";//标签名
    private static final int PRECEDENCE = 10000;//优先级

    public AdminTagProcessor(String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler) {

    }

}
