package com.github.md.web.user;

import com.github.md.web.user.auth.AuthService;
import com.github.md.web.user.auth.MRAuthInterceptDoer;
import com.github.md.web.user.auth.MRPermit;
import com.github.md.web.user.auth.MResource;
import com.github.md.web.user.auth.annotations.Authorize;
import com.github.md.web.user.auth.defaults.*;
import com.github.md.web.user.role.DefaultRoleService;
import com.github.md.web.user.role.RoleService;
import com.github.md.web.user.support.defaults.DefaultUserService;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证/鉴权 注册器。
 *
 * @author pengxg
 * @date 2022/4/25 10:38 上午
 */
@Getter(value = AccessLevel.MODULE)
public class AuthenticationRegistry {
    @Setter
    private UserService userService;
    @Setter
    private LoginService loginService;
    @Setter
    private RoleService roleService;
    @Setter
    private AuthService authService;
    @Setter
    private UserInterceptDoer userInterceptDoer;
    private List<MRAuthInterceptDoer> authInterceptDoers = Lists.newArrayList();
    private final Map<Class<? extends MResource>, MRPermit> resourcePermitMapping = new HashMap<>();


    AuthenticationRegistry() {
        // 默认
        userService = new DefaultUserService();
        loginService = new DefaultUserService();
        roleService = new DefaultRoleService();
        authService = new DefaultAuthService();
        userInterceptDoer = new DefaultUserInterceptDoer();

        // 内置的权限判定执行器
        MRAuthInterceptDoer doer = new MRAuthInterceptDoer() {
            @Override
            public int order() {
                return 0;
            }

            @Override
            public boolean preAuth(HttpServletRequest request, HttpServletResponse response, Object handler) {
                if (!(handler instanceof HandlerMethod)) {
                    return true;
                }

                MResource resource = (((HandlerMethod) handler).getMethodAnnotation(Authorize.class) != null)
                        ? ApiResourceFactory.createAnnotateApiResource(request, (HandlerMethod) handler)
                        : ApiResourceFactory.createMetaApiResource(request, (HandlerMethod) handler);

//                UserWithRolesWrapper user = AuthenticationManager.me().getUser(request);
                User user = UserThreadLocal.getUser();
                return AuthenticationManager.me().permit(user, resource);

            }
        };
        authInterceptDoers.add(doer);

        // 内置的资源-判定器映射
        resourcePermitMapping.put(MetaApiResource.class, new ApiResourcePermit()); // 基于数据的接口资源判定
        resourcePermitMapping.put(AnnotateApiResource.class, new AuthorizePermit()); // 基于注解的接口资源判定
    }

    /**
     * 设置自定义的用户服务。如果你需要更换内置的用户数据表，你需要配置此项
     *
     * @param userService
     * @return
     */
    public AuthenticationRegistry setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    /**
     * 设置自定义的登录服务。如果你不想用内存缓存登录用户，比如想使用redis缓存已登录的用户，那么可以配置此项
     *
     * @param loginService
     * @return
     */
    public AuthenticationRegistry setLoginService(LoginService loginService) {
        this.loginService = loginService;
        return this;
    }

    /**
     * 设置自定义的角色服务。如果你需要更换内置的角色数据表，你需要配置此项
     *
     * @param roleService
     * @return
     */
    public AuthenticationRegistry setRoleService(RoleService roleService) {
        this.roleService = roleService;
        return this;
    }

    /**
     * 设置自定义的权限服务。如果你需要更换内置的权限数据表，你需要配置此项
     *
     * @param authService
     * @return
     */
    public AuthenticationRegistry setAuthService(AuthService authService) {
        this.authService = authService;
        return this;
    }

    /**
     * 设置用户预识别执行器
     *
     * @param userInterceptDoer
     * @return
     */
    public AuthenticationRegistry setUserInterceptDoer(UserInterceptDoer userInterceptDoer) {
        this.userInterceptDoer = userInterceptDoer;
        return this;
    }


    /**
     * 配置自定义的权限判定执行器。权限拦截器可以配置多个，应用的顺序取决于 {@link MRAuthInterceptDoer#order()}
     *
     * @param authInterceptDoers
     */
    public AuthenticationRegistry config(List<MRAuthInterceptDoer> authInterceptDoers) {
        this.authInterceptDoers.addAll(authInterceptDoers);
        return this;
    }

    /**
     * 配置权限拦截器鉴权时的规则——即什么资源，使用什么权限判定器 来判定。这提供了一种灵活的方式。目前，内置判定器对接口资源进行判定。
     *
     * @param clazz
     * @param mrPermit
     * @return
     */
    public AuthenticationRegistry config(Class<? extends MResource> clazz, MRPermit mrPermit) {
        this.resourcePermitMapping.put(clazz, mrPermit);
        return this;
    }
}
