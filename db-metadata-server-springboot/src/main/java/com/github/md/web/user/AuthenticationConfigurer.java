package com.github.md.web.user;

import cn.com.asoco.annotation.Authorize;
import com.github.md.web.user.auth.*;
import com.github.md.web.user.auth.defaults.ApiResourceFactory;
import com.github.md.web.user.auth.defaults.DefaultAuthService;
import com.github.md.web.user.role.DefaultRoleService;
import com.github.md.web.user.role.RoleService;
import com.github.md.web.user.support.defaults.DefaultUserService;
import com.google.common.collect.Lists;
import org.springframework.web.method.HandlerMethod;

import java.util.List;
import java.util.Map;

/**
 * 鉴权模块配置。
 * <p>
 * 实现此接口，并注册Spring bean实现，认证授权的自定义
 *
 * @author pengxg
 * @date 2022/2/28 8:50 上午
 */
public interface AuthenticationConfigurer {

    /**
     * 配置用户认证执行器
     *
     * @return
     */
    default UserInterceptDoer configUserInterceptDoer() {
        return new DefaultUserInterceptDoer();
    }

    /**
     * 配置用户鉴权执行器
     *
     * @return
     */
    default List<MRAuthInterceptDoer> configAuthInterceptDoer() {
        MRAuthInterceptDoer doer = (request, response, handler) -> {
            if (!(handler instanceof HandlerMethod)) {
                return true;
            }

            MResource resource = (((HandlerMethod) handler).getMethodAnnotation(Authorize.class) != null)
                    ? ApiResourceFactory.createAnnotateApiResource(request, (HandlerMethod) handler)
                    : ApiResourceFactory.createMetaApiResource(request, (HandlerMethod) handler);

            User user = AuthenticationManager.me().getUser(request);
            return AuthenticationManager.me().permit(user, resource);
        };
        return Lists.newArrayList(doer);
    }

    /**
     * 配置资源-认证器映射关系。指定什么资源用什么认证器进行认证
     *
     * @param mapping
     */
    default void configPermitMapping(Map<Class<? extends MResource>, MRPermit> mapping) {

    }

    /**
     * 配置用户服务
     *
     * @return
     */
    default UserService configUserService() {
        return new DefaultUserService();
    }

    /**
     * 配置登录服务
     *
     * @return
     */
    default LoginService configLoginService() {
        return new DefaultUserService();
    }

    /**
     * 配置角色服务
     *
     * @return
     */
    default RoleService configRoleService() {
        return new DefaultRoleService();
    }

    /**
     * 配置权限服务
     *
     * @return
     */
    default AuthService configAuthService() {
        return new DefaultAuthService();
    }
}
