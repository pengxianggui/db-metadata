package com.github.md.web.user;

import cn.com.asoco.util.AssertUtil;
import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.kit.Kv;
import com.github.md.web.user.auth.*;
import com.github.md.web.user.auth.defaults.AnnotateApiResource;
import com.github.md.web.user.auth.defaults.ApiResourcePermit;
import com.github.md.web.user.auth.defaults.AuthorizePermit;
import com.github.md.web.user.auth.defaults.MetaApiResource;
import com.github.md.web.user.role.RoleService;
import com.github.md.web.user.role.UserWithRolesWrapper;
import com.github.md.web.user.support.defaults.DefaultTokenGenerator;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jfinal.kit.StrKit;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    private UserService userService;
    private LoginService loginService;
    private RoleService roleService;
    private AuthService authService;

    @Getter
    private UserIntercept userIntercept; // 用户认证拦截器
    @Getter
    private MRAuthIntercept authIntercept; // 用户鉴权拦截器

    /**
     * 资源、判定器映射表，指定资源要使用的判定器
     */
    private static final Map<Class<? extends MResource>, MRPermit> resourcePermitMapping = new HashMap<>();

    public static AuthenticationManager me() {
        AuthenticationConfigurer configurer = AnalysisSpringUtil.getBean(AuthenticationConfigurer.class);
        if (me.userService == null) {
            me.userService = configurer.configUserService();
        }
        if (me.loginService == null) {
            me.loginService = configurer.configLoginService();
        }
        if (me.roleService == null) {
            me.roleService = configurer.configRoleService();
        }
        if (me.authService == null) {
            me.authService = configurer.configAuthService();
        }

        if (me.userIntercept == null) {
            me.userIntercept = new UserIntercept(configurer.configUserInterceptDoer());
        }
        if (me.authIntercept == null) {
            me.authIntercept = new MRAuthIntercept(configurer.configAuthInterceptDoer());
        }

        // 配置默认鉴权控制
        resourcePermitMapping.put(MetaApiResource.class, new ApiResourcePermit()); // 基于数据的接口资源判定
        resourcePermitMapping.put(AnnotateApiResource.class, new AuthorizePermit()); // 基于注解的接口资源判定
        configurer.configPermitMapping(resourcePermitMapping);

        return me;
    }

    private AuthenticationManager() {
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

    /**
     * 兼容ROOT登录
     *
     * @param uid
     * @param pwd
     * @return
     */
    public LoginVO login(String uid, String pwd) {
        String loginKey = loginService.loginKey();
        String pwdKey = loginService.pwdKey();

        Kv rootKv = Root.me().attrs();

        UserWithRolesWrapper userWithRolesWrapper;
        if (uid.equals(rootKv.get(loginKey)) && pwd.equals(rootKv.get(pwdKey))) { // 优先鉴定ROOT
            userWithRolesWrapper = Root.me();
            String token = new DefaultTokenGenerator().generate(userWithRolesWrapper);
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
            String token = new DefaultTokenGenerator().generate(user);
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
            String token = new DefaultTokenGenerator().generate(user);
            getLoginUsers().invalidate(token);
            return true;
        }

        return loginService.logout(user);
    }
}
