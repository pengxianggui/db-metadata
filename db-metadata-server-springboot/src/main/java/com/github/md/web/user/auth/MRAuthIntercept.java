package com.github.md.web.user.auth;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * <p> @Date : 2019/12/17 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class MRAuthIntercept implements HandlerInterceptor {

    private Collection<MRAuthInterceptDoer> interceptDoers;

    public MRAuthIntercept(Collection<MRAuthInterceptDoer> interceptDoers) {
        this.interceptDoers = CollectionUtils.isEmpty(interceptDoers) ? Lists.newArrayList() : interceptDoers;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean flag = interceptDoers.stream().allMatch(doer -> doer.preAuth(request, response, handler));
        if (!flag) {
            throw new MRException("无权限访问!");
        }
        return true;
    }

    //    private List<String> skipPathPatterns = Lists.newArrayList(UserRouter.URL_LOGIN);
    //
    //    @Override
    //    public void intercept(Invocation inv) {
    //        // 放行
    //        if (PatternPathMatcher.matchAny(inv.getActionKey(),
    //                skipPathPatterns.toArray(new String[skipPathPatterns.size()]))) {
    //            inv.invoke();
    //            return;
    //        }
    //
    //        MResource resource = MRManager.me().getResource(inv.getActionKey());
    //        User user = UserThreadLocal.getUser();
    //        if (user == null) {
    //            throw new UserException("未从UserThreadLocal中取得用户信息");
    //        }
    //        if (resource != null) {
    //            log.debug("{} 资源鉴权,资源信息:{}-{}-{}", inv.getActionKey(), resource.mResourceId(), resource.mResourceName(), resource.needPermit());
    //            if (!MRManager.me().permit(user, resource)) {
    //                throw new MRException("用户无法访问该资源userId:%s-%s,ResourceID:%s", user.userId(), user.userName(), resource.mResourceId());
    //            }
    //        }
    //        inv.invoke();
    //    }
    //
    //    public void addSkipPathPatterns(String... skipPathPatterns) {
    //        Preconditions.checkNotNull(skipPathPatterns);
    //        this.skipPathPatterns.addAll(Lists.newArrayList(skipPathPatterns));
    //    }
}
