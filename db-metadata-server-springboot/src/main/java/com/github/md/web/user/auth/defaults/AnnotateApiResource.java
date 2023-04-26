package com.github.md.web.user.auth.defaults;

import com.github.md.web.user.auth.MResource;
import com.github.md.web.user.auth.annotations.Authorize;
import lombok.Getter;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 基于注解的api接口资源。基于{@link Authorize}的资源描述
 *
 * @author pengxg
 * @date 2022/2/21 2:34 下午
 */
public class AnnotateApiResource implements MResource {
    @Getter
    private Authorize authorize;
    @Getter
    private HttpServletRequest request;
    private boolean needPermit;

    protected AnnotateApiResource(Authorize authorize, HttpServletRequest request) {
        this.authorize = authorize;
        this.needPermit = !(authorize == null || authorize.whoever());
        this.request = request;
    }

    @Deprecated
    public static AnnotateApiResource by(HttpServletRequest request, HandlerMethod handler) {
        Authorize authorize = handler.getMethodAnnotation(Authorize.class);
        return new AnnotateApiResource(authorize, request);
    }

    public static AnnotateApiResource by(HttpServletRequest request, Authorize authorize) {
        return new AnnotateApiResource(authorize, request);
    }

    @Override
    public String mResourceId() {
        return request.getRequestURI() + "." + request.getMethod();
    }

    @Override
    public String mResourceName() {
        return mResourceId();
    }

    @Override
    public boolean needPermit() {
        return needPermit;
    }
}
