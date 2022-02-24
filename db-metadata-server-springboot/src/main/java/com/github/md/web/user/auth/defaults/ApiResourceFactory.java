package com.github.md.web.user.auth.defaults;

import cn.com.asoco.annotation.Authorize;
import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.web.config.QuickJudge;
import com.github.md.web.query.QueryHelper;
import com.github.md.web.user.auth.annotations.MetaAccess;
import com.github.md.web.user.auth.annotations.Type;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pengxg
 * @date 2022/2/23 4:23 下午
 */
public class ApiResourceFactory {

    /**
     * 构建基于注解的api接口资源
     *
     * @param request
     * @param handler
     * @return
     */
    public static AnnotateApiResource createAnnotateApiResource(HttpServletRequest request, HandlerMethod handler) {
        Authorize authorize = handler.getMethodAnnotation(Authorize.class);
        return new AnnotateApiResource(authorize, request);
    }

    /**
     * 构建基于数据的api接口资源
     *
     * @param request
     * @param handlerMethod
     * @return
     */
    public static MetaApiResource createMetaApiResource(HttpServletRequest request, HandlerMethod handlerMethod) {
        String uri = removeEndSlash(request.getServletPath());

        Type type;
        String metaCode = null;
        MetaAccess access = handlerMethod.getMethodAnnotation(MetaAccess.class);
        if (access == null || access.value() == Type.API) {
            type = Type.API;
            return getApiResource(type, uri);
        }

        type = access.value();
        QueryHelper queryHelper = new QueryHelper(((QuickJudge) AnalysisSpringUtil.getBean(QuickJudge.class)).getRequest());
        switch (type) {
            case API_WITH_META_OBJECT:
                metaCode = queryHelper.getObjectCode();
                break;
            case API_WITH_META_FEATURE:
                metaCode = queryHelper.getFeatureCode();
                break;
        }

        return getApiResource(type, metaCode, uri);
    }

    private static MetaApiResource getApiResource(Type type, String metaCode, String uri) {
        Record record = SpringAnalysisManager.me().dbMain().findFirst("select * from meta_api_resource where type=? and uri=? and meta_code=?",
                type.getCode(), uri, metaCode);
        if (record == null) {
            return null;
        }
        return new MetaApiResource(record);
    }

    private static MetaApiResource getApiResource(Type type, String uri) {
        Record record = SpringAnalysisManager.me().dbMain().findFirst("select * from meta_api_resource where type=? and uri=? and (meta_code=null or meta_code = '')", type.getCode(), uri);
        if (record == null) {
            return null;
        }
        return new MetaApiResource(record);
    }

    /**
     * 移除尾部/
     *
     * @param servletPath
     * @return
     */
    private static String removeEndSlash(String servletPath) {
        if (servletPath.endsWith("/")) {
            return servletPath.substring(0, servletPath.length() - 1);
        }
        return servletPath;
    }
}
