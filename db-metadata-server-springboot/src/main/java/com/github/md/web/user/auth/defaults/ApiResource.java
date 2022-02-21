package com.github.md.web.user.auth.defaults;

import cn.com.asoco.annotation.Authorize;
import com.github.md.web.user.auth.MResource;
import lombok.Getter;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * api接口资源。基于{@link Authorize}的资源描述
 *
 * @author pengxg
 * @date 2022/2/21 2:34 下午
 */
public class ApiResource implements MResource {
    @Getter
    private Authorize authorize;
    @Getter
    private HttpServletRequest request;
    private boolean needPermit;

    private ApiResource(Authorize authorize, HttpServletRequest request) {
        this.authorize = authorize;
        this.needPermit = !(authorize == null || authorize.whoever());
        this.request = request;
    }

    public static ApiResource by(HttpServletRequest request, HandlerMethod handler) {
        Authorize authorize = handler.getMethodAnnotation(Authorize.class);
        return new ApiResource(authorize, request);
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
