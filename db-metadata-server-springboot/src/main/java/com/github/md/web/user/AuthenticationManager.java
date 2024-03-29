package com.github.md.web.user;

import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.web.kit.AssertKit;
import com.github.md.web.user.auth.*;
import com.github.md.web.user.role.RoleService;
import com.github.md.web.user.role.UserWithRolesWrapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 认证管理
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class AuthenticationManager {
    private static AuthenticationManager me = null;

    /**
     * 需要注意得是loginUsers的put动作 只能由loginService来做。key一般为用户token
     */
    @Getter
    private final Cache<String, UserWithRolesWrapper> loginUsers = CacheBuilder.newBuilder()
            .maximumSize(10000).expireAfterWrite(7, TimeUnit.DAYS).build();

    @Getter
    private final UserService userService;
    @Getter
    private final LoginService loginService;
    @Getter
    private final RoleService roleService;
    @Getter
    private final AuthService authService;
    @Getter
    private final UserIntercept userIntercept; // 用户认证拦截器
    @Getter
    private final MRAuthIntercept authIntercept; // 用户鉴权拦截器

    /**
     * 资源、判定器映射表，指定资源要使用的判定器
     */
    private final Map<Class<? extends MResource>, MRPermit> resourcePermitMapping;

    public static AuthenticationManager me() {
        if (me == null) {
            synchronized (AuthenticationManager.class) {
                if (me == null) {
                    AuthenticationConfigurer configurer = AnalysisSpringUtil.getBean(AuthenticationConfigurer.class);
                    config(configurer);
                }
            }
        }
        return me;
    }

    public static void config(AuthenticationConfigurer configurer) {
        me = new AuthenticationManager(configurer);
    }

    public AuthenticationManager(AuthenticationConfigurer configurer) {
        AuthenticationRegistry registry = new AuthenticationRegistry();
        configurer.configAuthentication(registry);
        userService = registry.getUserService();
        loginService = registry.getLoginService();
        roleService = registry.getRoleService();
        authService = registry.getAuthService();
        userIntercept = new UserIntercept(registry.getUserInterceptDoer());
        authIntercept = new MRAuthIntercept(registry.getAuthInterceptDoers());
        resourcePermitMapping = registry.getResourcePermitMapping();
        me = this;
    }

    /**
     * 鉴权: 判定用户是否拥有某个资源的访问权限。你应当在你的 {@link MRAuthInterceptDoer}中执行此鉴权函数
     *
     * @param user      用户
     * @param mResource 资源
     * @return
     */
    public boolean permit(User user, MResource mResource) {
        if (mResource == null || !mResource.needPermit()) { // 直接放行的资源无需鉴权
            return true;
        }

        // 此资源需要鉴权，必定需要用户登录
        AssertKit.isTrue(user != null, new UnLoginException("会话过期，请重新登录"));

        if (user.isRoot()) { // root不校验
            return true;
        }

        for (Map.Entry<Class<? extends MResource>, MRPermit> entry : resourcePermitMapping.entrySet()) {
            if (entry.getKey().isInstance(mResource)) {
                return entry.getValue().permit(user, mResource);
            }
        }

        log.error("资源%s无法确定应该使用什么判定器进行判定, 视为无权限！请通过dbmeta扩展配置进行指定。", mResource.getClass().toString());
        return false;
    }
}
