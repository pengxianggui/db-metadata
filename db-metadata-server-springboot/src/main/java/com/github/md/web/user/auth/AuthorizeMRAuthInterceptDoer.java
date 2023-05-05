package com.github.md.web.user.auth;

import com.github.md.web.user.AuthenticationManager;
import com.github.md.web.user.User;
import com.github.md.web.user.UserThreadLocal;
import com.github.md.web.user.auth.annotations.Authorize;
import com.github.md.web.user.auth.defaults.ApiResourceFactory;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于dbmeta内置注解{@link Authorize}的鉴权拦截执行器。优先级高于{@link DataMRAuthInterceptDoer}，
 * 若此鉴权拦截执行器判定有权限，则{@link DataMRAuthInterceptDoer}不会再参与判定了。
 *
 * @author pengxg
 * @date 2023/5/5 14:21
 */
public class AuthorizeMRAuthInterceptDoer implements MRAuthInterceptDoer {
    @Override
    public int order() {
        return 0;
    }

    @Override
    public boolean interruptAuthChain(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    @Override
    public boolean support(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return (handler instanceof HandlerMethod)
                && ((HandlerMethod) handler).hasMethodAnnotation(Authorize.class);
    }

    @Override
    public boolean preAuth(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        MResource resource = ApiResourceFactory.createAnnotateApiResource(request, (HandlerMethod) handler);
//                UserWithRolesWrapper user = AuthenticationManager.me().getUser(request);
        User user = UserThreadLocal.getUser();
        return AuthenticationManager.me().permit(user, resource);

    }
}
