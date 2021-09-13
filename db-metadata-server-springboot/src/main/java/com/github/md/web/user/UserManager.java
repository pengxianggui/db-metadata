package com.github.md.web.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.github.md.web.user.role.UserWithRolesWrapper;
import com.github.md.web.user.support.local.LocalUserFactory;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

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

    private UserFactory userFactory;

    private UserIntercept userIntercept;

    public static UserManager me() {
        if (me.userFactory == null) {
            me.userFactory = new LocalUserFactory();
        }

        if (me.userIntercept == null) {
            me.userIntercept = new UserIntercept();
        }
        return me;
    }

    public UserFactory getUserFactory() {
        return userFactory;
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

    public UserIntercept getUserIntercept() {
        return userIntercept;
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
}
