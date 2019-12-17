package com.hthjsj.web.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hthjsj.web.WebException;
import com.hthjsj.web.auth.MRManager;
import com.hthjsj.web.auth.MResource;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p> @Date : 2019/10/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class UserAuthIntercept implements Interceptor {

    public static Cache<String, User> caches = CacheBuilder.newBuilder().maximumSize(100).build();

    static User user = new User() {

        @Override
        public String userId() {
            return "db-meta-web-jfinal";
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

    @Override
    public void intercept(Invocation inv) {
        /**
         * 获取 LoginService ,进行登录校验
         * 1. 获取LoginService实例
         * 2. 从Request中取得用户;
         *  2.1 取id|token|或其他
         *  2.2 从某个容器中获取用户信息(redis|db|缓存|其他)
         * 3. 判断用户是否已登录,是否过期
         *  3.1 已登录,未过期 -> 放行
         *  3.2 未登录->拒绝
         *  3.3 已登录,已过期 -> 拒绝
         *  > 已登录和过期 ,用两种状态表示, 用是否过期来表示用户是否登录虽然也可以,但是语义不明确
         * 4. 放行后更新用户过期时间.
         */
        try {
            if (inv.getActionKey().startsWith(UserRouter.URL_PREFIX) && !inv.getActionKey().endsWith("update")) {
                inv.invoke();
                return;
            }
            LoginService<User> loginService = UserManager.me().loginService();
            User user = loginService.getUser(inv.getController().getRequest());
            if (user != null) {
                MResource resource = MRManager.me().getResource(inv.getActionKey());
                if (resource != null) {
                    if (!MRManager.me().permit(user, resource)) {
                        throw new WebException("用户无法访问该资源userId:%s-%s,ResourceID:%s", user.userId(), user.userName(), resource.mResourceId());
                    }
                }
                log.debug("{} 资源放行", inv.getActionKey());
                UserThreadLocal.setUser(user);
                inv.invoke();
                inv.getController().setCookie(loginService.cookieKey(), user.userId(), (int) TimeUnit.HOURS.toSeconds(6));
            } else {
                throw new WebException("未从请求内发现有效用户标志,请检查参数:%s", loginService.tokenKey());
            }
        } finally {
            UserThreadLocal.removeUser(user);
        }
    }
}
