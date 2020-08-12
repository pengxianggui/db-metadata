package com.hthjsj.web.query.dynamic;

import com.jfinal.template.Engine;
import com.jfinal.template.Template;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class CompileRuntime {

    private final Engine engine = new Engine("sql-compile");

    public CompileRuntime() {
    }

    public String compile(String sqlTemplate) {
        Template template = engine.getTemplateByString(sqlTemplate);
        Context context = new ExtCompileDataContext();
        String result = template.renderToString(context.data());
        log.info("Compile sqlTemplate:{}", result);
        return result;
    }

    public String compile(String sqlTemplate, HttpServletRequest request) {
        Template template = engine.getTemplateByString(sqlTemplate);
        Context context = new DefaultContext();
        UserVariable userVariable = new UserVariable(request);
        context.set(userVariable.name(), userVariable.init());
        String result = template.renderToString(context.data());
        log.info("Compile sqlTemplate:{}", result);
        return result;
    }

    public String compile(String sqlTemplate, Context context) {
        Template template = engine.getTemplateByString(sqlTemplate);
        String result = template.renderToString(context.data());
        log.info("Compile sqlTemplate:{}", result);
        return result;
    }
}
