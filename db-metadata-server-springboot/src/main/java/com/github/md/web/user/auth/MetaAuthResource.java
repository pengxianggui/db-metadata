package com.github.md.web.user.auth;

import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.meta.AuthForType;
import com.github.md.analysis.meta.AuthTypeRefered;
import com.github.md.web.config.QuickJudge;
import com.github.md.web.query.QueryHelper;
import lombok.Getter;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 兼容元编码的接口资源(meta_auth表)
 *
 * @author pengxg
 * @date 2021/10/15 9:51 上午
 */
public class MetaAuthResource implements MResource {
    @Getter
    private String uri;
    @Getter
    private AuthForType type;
    @Getter
    private String metaCode;
    private boolean needPermit = true;

    MetaAuthResource(HttpServletRequest request, HandlerMethod handlerMethod) {
        this.uri = removeEndSlash(request.getServletPath());
        AuthTypeRefered authTypeRefered = handlerMethod.getMethodAnnotation(AuthTypeRefered.class);
        if (Objects.isNull(authTypeRefered)) {
            this.needPermit = false;
            return;
        }

        this.type = authTypeRefered.value();
        QueryHelper queryHelper = new QueryHelper(((QuickJudge) AnalysisSpringUtil.getBean(QuickJudge.class)).getRequest());
        switch (this.type) {
            case API_WITH_META_OBJECT:
                this.metaCode = queryHelper.getObjectCode();
                break;
            case API_WITH_META_FEATURE:
                this.metaCode = queryHelper.getFeatureCode();
                break;
        }
    }

    private String removeEndSlash(String servletPath) {
        if (servletPath.endsWith("/")) {
            return servletPath.substring(0, servletPath.length() - 1);
        }
        return servletPath;
    }

    @Override
    public String mResourceId() {
        return type.name() + ":" + metaCode + ":" + uri;
    }

    @Override
    public String mResourceName() {
        return null;
    }

    @Override
    public boolean needPermit() {
        return needPermit;
    }
}
