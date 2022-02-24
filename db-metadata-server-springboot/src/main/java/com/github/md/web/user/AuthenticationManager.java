package com.github.md.web.user;

import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.kit.Kv;
import com.github.md.web.user.auth.AuthService;
import com.github.md.web.user.auth.defaults.DefaultAuthService;
import com.github.md.web.user.role.DefaultRoleService;
import com.github.md.web.user.role.MRRole;
import com.github.md.web.user.role.RoleService;
import com.github.md.web.user.role.UserWithRolesWrapper;
import com.github.md.web.user.support.defaults.DefaultUserService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 认证管理
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class AuthenticationManager {

    private static final AuthenticationManager me = new AuthenticationManager();

    /**
     * 需要注意得是loginUsers得put动作 只能由loginService来做
     */
    @Getter
    private final Cache<String, UserWithRolesWrapper> loginUsers = CacheBuilder.newBuilder().maximumSize(1000).build();

    @Deprecated
    @Getter
    private UserFactory userFactory;

    private UserService userService;
    private LoginService loginService;
    private RoleService roleService;
    private AuthService authService;

    @Getter
    private UserIntercept userIntercept;

    public static AuthenticationManager me() {
        if (me.userService == null) {
            me.userService = new DefaultUserService();
        }
        if (me.loginService == null) {
            me.loginService = new DefaultUserService();
        }
        if (me.userIntercept == null) {
            UserInterceptDoer doer = AnalysisSpringUtil.getBean(UserInterceptDoer.class);
            me.userIntercept = new UserIntercept(doer);
        }
        if (me.roleService == null) {
            me.roleService = new DefaultRoleService();
        }
        if (me.authService == null) {
            me.authService = new DefaultAuthService();
        }
        return me;
    }

    private AuthenticationManager() {
    }

    @Deprecated
    public void setUserFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    public UserService userService() {
        return this.userService;
    }

    public LoginService loginService() {
        return this.loginService;
    }

    public RoleService roleService() {
        return this.roleService;
    }

    public AuthService authService() {
        return this.authService;
    }

    public AuthenticationManager config(UserService userService) {
        this.userService = userService;
        return this;
    }

    public AuthenticationManager config(LoginService loginService) {
        this.loginService = loginService;
        return this;
    }

    public AuthenticationManager config(RoleService roleService) {
        this.roleService = roleService;
        return this;
    }

    public AuthenticationManager config(AuthService authService) {
        this.authService = authService;
        return this;
    }

    public UserWithRolesWrapper getUser(HttpServletRequest request) {
        return this.loginService.getUser(request);
    }

    @Deprecated
    public UserWithRolesWrapper getUserRole(HttpServletRequest request) {
        User user = getUser(request);
        if (user instanceof UserWithRolesWrapper) {
            return (UserWithRolesWrapper) user;
        }
        throw new UserException("当前User实例中并不包含Role信息");
    }

    public static final User staticUser = new UserWithRolesWrapper() {

        @Override
        public MRRole[] roles() {
            return new MRRole[0];
        }

        @Override
        public boolean hasRole(String nameOrCode) {
            return false;
        }

        @Override
        public String userId() {
            return "SYSTEM";
        }

        @Override
        public String userName() {
            return null;
        }

        @Override
        public Kv attrs() {
            return null;
        }

        @Override
        public Kv attrs(Map attrs) {
            return null;
        }
    };
}
