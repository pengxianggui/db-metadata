package com.github.md.web.user.auth;

import com.github.md.web.user.AuthenticationManager;
import com.github.md.web.user.User;
import com.github.md.web.user.UserThreadLocal;
import com.github.md.web.user.auth.defaults.ApiResourceFactory;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于动态数据的权限拦截执行器。此拦截执行器支持所有接口(HandlerMethod类型)，且优先级最低。
 * <p>
 * 动态数据指的是dbmeta内置的表：meta_api_resource，此表维护了api接口的鉴权依据。若表中未对某个接口配置鉴权依据，则表示此接口不鉴权直接放行，
 * 若配置了，则按照配置的规则对当前访问用户进行鉴权。
 *
 * @author pengxg
 * @date 2023/5/5 14:25
 */
public class DataMRAuthInterceptDoer implements MRAuthInterceptDoer {
    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean support(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return (handler instanceof HandlerMethod);
    }

    @Override
    public boolean preAuth(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MResource resource = ApiResourceFactory.createMetaApiResource(request, (HandlerMethod) handler);
        User user = UserThreadLocal.getUser();
        return AuthenticationManager.me().permit(user, resource);
    }
}
