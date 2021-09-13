package com.github.md.web.query.dynamic;

import com.jfinal.kit.StrKit;
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
        if (StrKit.isBlank(sqlTemplate)) {
            return "";
        }
        String result;
        try {
            Template template = engine.getTemplateByString(sqlTemplate);
            Context context = new DefaultContext();
            UserVariable userVariable = new UserVariable(request);
            context.set(userVariable.name(), userVariable.init());
            result = template.renderToString(context.data());
            log.info("Compile sqlTemplate:{}", result);
        } catch (Exception e) {
            throw new QueryCompileException("动态SQL: %s 编译出错,错误信息:%s ", sqlTemplate, e.getMessage());
        }
        return result;
    }

    public String compile(String sqlTemplate, HttpServletRequest request, Context context) {
        if (StrKit.isBlank(sqlTemplate)) {
            return "";
        }
        String result;
        try {
            Template template = engine.getTemplateByString(sqlTemplate);
            UserVariable userVariable = new UserVariable(request);
            context.set(userVariable.name(), userVariable.init());
            result = template.renderToString(context.data());
            log.info("Compile sqlTemplate:{}", result);
        } catch (Exception e) {
            throw new QueryCompileException("动态SQL: %s 编译出错,错误信息:%s ", sqlTemplate, e.getMessage());
        }
        return result;
    }

    public String compile(String sqlTemplate, Context context) {
        Template template = engine.getTemplateByString(sqlTemplate);
        String result = template.renderToString(context.data());
        log.info("Compile sqlTemplate:{}", result);
        return result;
    }
}
