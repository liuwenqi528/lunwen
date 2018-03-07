package com.khcm.user.web.admin.dialect;

import com.khcm.user.web.admin.dialect.tag.AdminTagProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义标签处理
 * Created by yangwb on 2017/12/29.
 */
public class AminDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "AminDialect";//定义方言名称

    public AminDialect() {
        super(DIALECT_NAME, "admin", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> processors = new HashSet<>();
        processors.add(new AdminTagProcessor(DIALECT_NAME));
        processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
    }
}
