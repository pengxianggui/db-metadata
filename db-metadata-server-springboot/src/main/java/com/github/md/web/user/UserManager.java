package com.github.md.web.user;

import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.kit.Kv;
import com.github.md.web.user.auth.MRRole;
import com.github.md.web.user.auth.Permission;
import com.github.md.web.user.role.UserWithRolesWrapper;
import com.github.md.web.user.support.local.LocalUserFactory;
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

    @Getter
    private UserFactory userFactory;

    @Getter
    private UserIntercept userIntercept;

    public static UserManager me() {
        if (me.userFactory == null) {
            me.userFactory = new LocalUserFactory();
        }

        if (me.userIntercept == null) {
            UserInterceptDoer doer = AnalysisSpringUtil.getBean(UserInterceptDoer.class);
            me.userIntercept = new UserIntercept(doer);
        }
        return me;
    }

    public void setUserFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    public UserService userService() {
        return getUserFactory().userService();
    }

    public LoginService loginService() {
        return getUserFactory().loginService();
    }

    public User getUser(HttpServletRequest request) {
        return loginService().getUser(request);
    }

    /**
     * 指定某用户为登录状态
     *
     * @param user
     */
    public void assignUserToLogged(User user) {
        loginService().login(user);
    }

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
        public Permission[] permissions() {
            return new Permission[0];
        }

        @Override
        public boolean hasPermission(String... permissions) {
            return false;
        }

        @Override
        public boolean hasPermission(Permission... permissions) {
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
