package com.github.md.web.user;

import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.kit.Kv;
import com.github.md.web.user.auth.AuthService;
import com.github.md.web.user.auth.defaults.DefaultAuthService;
import com.github.md.web.user.auth.meta.MetaAuthService;
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
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UserManager {

    private static final UserManager me = new UserManager();

    /**
     * 需要注意得是loginUsers得put动作 只能由loginService来做
     */
    @Getter
    private final Cache<String, User> loginUsers = CacheBuilder.newBuilder().maximumSize(1000).build();

    @Deprecated
    @Getter
    private UserFactory userFactory;

    private UserService userService;
    private LoginService loginService;
    private RoleService roleService;
    private AuthService authService;

    @Getter
    private final MetaAuthService metaAuthService = new MetaAuthService(); // 内置基于meta_auth的权限服务，dbMeta内置接口的鉴权必须使用此服务

    @Getter
    private UserIntercept userIntercept;

    public static UserManager me() {
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

    private UserManager() {
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

    public User getUser(HttpServletRequest request) {
        return this.loginService.getUser(request);
    }

    /**
     * 指定某用户为登录状态
     *
     * @param user
     */
    public void assignUserToLogged(User user) {
        this.loginService.login(user);
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
            return "游客";
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
