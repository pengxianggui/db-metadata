package com.hthjsj.web.auth;

import com.hthjsj.web.user.User;
import com.hthjsj.web.user.UserException;
import com.hthjsj.web.user.UserThreadLocal;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> @Date : 2019/12/17 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class MRIntercept implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        MResource resource = MRManager.me().getResource(inv.getActionKey());
        User user = UserThreadLocal.getUser();
        if (user == null) {
            throw new UserException("未从UserThreadLocal中取得用户信息");
        }
        if (resource != null) {
            log.debug("{} 资源鉴权,资源信息:{}-{}-{}", inv.getActionKey(), resource.mResourceId(), resource.mResourceName(), resource.needPermit());
            if (!MRManager.me().permit(user, resource)) {
                throw new MRException("用户无法访问该资源userId:%s-%s,ResourceID:%s", user.userId(), user.userName(), resource.mResourceId());
            }
        }
        inv.invoke();
    }
}
