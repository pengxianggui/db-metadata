package com.github.md.web.user;

import cn.com.asoco.util.AssertUtil;
import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.web.DbMetaConfigurer;
import com.github.md.web.kit.PassKit;
import com.github.md.web.user.auth.*;
import com.github.md.web.user.role.RoleService;
import com.github.md.web.user.role.UserWithRolesWrapper;
import com.github.md.web.user.support.defaults.JWTTokenGenerator;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jfinal.kit.StrKit;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        return me;
    }

    private AuthenticationManager() {
        AuthenticationConfigurer configurer = AnalysisSpringUtil.getBean(DbMetaConfigurer.class);
        AuthenticationRegistry registry = new AuthenticationRegistry();
        configurer.configAuthentication(registry);
        userService = registry.getUserService();
        loginService = registry.getLoginService();
        roleService = registry.getRoleService();
        authService = registry.getAuthService();
        userIntercept = new UserIntercept(registry.getUserInterceptDoer());
        authIntercept = new MRAuthIntercept(registry.getAuthInterceptDoers());
        resourcePermitMapping = registry.getResourcePermitMapping();
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
        AssertUtil.isTrue(user != null, new UnLoginException("会话过期，请重新登录"));

        if (isRoot(user)) {
            return true; // ROOT权限一切放行
        }

        for (Map.Entry<Class<? extends MResource>, MRPermit> entry : resourcePermitMapping.entrySet()) {
            if (entry.getKey().isInstance(mResource)) {
                return entry.getValue().permit(user, mResource);
            }
        }
        throw new MRException("资源%s无法确定应该使用什么判定器进行判定, 请在%s中指定",
                mResource.getClass().toString(), this.getClass().toString());
    }

    /**
     * 判断是否为ROOT用户
     *
     * @param user
     * @return
     */
    public boolean isRoot(User user) {
        return Root.me().equals(user);
    }

    /**
     * 兼容ROOT登录
     *
     * @param uid
     * @param pwd
     * @return
     */
    public LoginVO login(String uid, String pwd) {
        Root root = Root.me();
        UserWithRolesWrapper userWithRolesWrapper;
        if (uid.equals(root.getLoginName())) { // 优先鉴定ROOT
            String encryptedPass = PassKit.encryptPass(pwd);
            AssertUtil.isTrue(encryptedPass.equals(root.getLoginPass()), "密码错误！"); // TODO 超过4次将锁定ROOT账号

            userWithRolesWrapper = Root.me();
            String token = new JWTTokenGenerator().generate(userWithRolesWrapper);
            AuthenticationManager.me().getLoginUsers().put(token, userWithRolesWrapper);
            return DefaultLoginVO.builder().token(token)
                    .id(userWithRolesWrapper.userId())
                    .username(userWithRolesWrapper.userName())
                    .avatar(userWithRolesWrapper.avatar())
                    .attrs(userWithRolesWrapper.attrs()).build();
        }

        userWithRolesWrapper = loginService.login(uid, pwd);
        AssertUtil.isTrue(userWithRolesWrapper != null, "用户名或密码输入错误");
        return loginService.setLogged(userWithRolesWrapper);
    }

    /**
     * 获取当前登录用户, 兼容ROOT
     *
     * @param request
     * @return
     */
    public UserWithRolesWrapper getUser(HttpServletRequest request) {
        String token = request.getHeader(loginService.tokenKey());
        if (StrKit.isBlank(token)) {
            return null;
        }

        UserWithRolesWrapper user = getLoginUsers().getIfPresent(token);
        if (isRoot(user)) {
            return user;
        }

        return this.loginService.getUser(request);
    }

    /**
     * 获取当前登录的用户信息，兼容ROOT
     *
     * @param request
     * @return
     */
    public LoginVO getInfo(HttpServletRequest request) {
        UserWithRolesWrapper user = getUser(request);
        if (isRoot(user)) {
            String token = new JWTTokenGenerator().generate(user);
            return DefaultLoginVO.builder().token(token)
                    .id(user.userId())
                    .username(user.userName())
                    .avatar(user.avatar())
                    .attrs(user.attrs()).build();
        }

        return this.loginService.getInfo(request);
    }

    /**
     * 兼容ROOT登出
     *
     * @param user
     * @return
     */
    public boolean logout(UserWithRolesWrapper user) {
        if (isRoot(user)) {
            getLoginUsers().invalidate(user.userId());
            return true;
        }

        return loginService.logout(user);
    }
}
