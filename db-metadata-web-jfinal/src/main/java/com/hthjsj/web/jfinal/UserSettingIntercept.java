package com.hthjsj.web.jfinal;

import com.hthjsj.web.ThreadLocalUserKit;
import com.hthjsj.web.User;
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
            ThreadLocalUserKit.setUser(user);
            inv.invoke();
        } finally {
            ThreadLocalUserKit.removeUser(user);
        }
    }
}
