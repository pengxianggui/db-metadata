package com.hthjsj.web.jfinal;

import com.hthjsj.web.User;
import com.hthjsj.web.UserThreadLocal;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * <p> @Date : 2019/10/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UserSettingIntercept implements Interceptor {

    static User user = new User() {

        @Override
        public String userId() {
            return "db-meta-web-jfinal";
        }
    };

    @Override
    public void intercept(Invocation inv) {
        try {
            UserThreadLocal.setUser(user);
            inv.invoke();
        } finally {
            UserThreadLocal.removeUser(user);
        }
    }
}
