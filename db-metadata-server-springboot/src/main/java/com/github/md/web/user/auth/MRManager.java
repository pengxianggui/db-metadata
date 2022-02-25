package com.github.md.web.user.auth;

import cn.com.asoco.util.AssertUtil;
import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.web.user.UnLoginException;
import com.github.md.web.user.User;
import com.github.md.web.user.auth.defaults.MetaApiResource;
import com.github.md.web.user.auth.defaults.AnnotateApiResource;
import com.github.md.web.user.auth.defaults.AuthorizePermit;
import com.github.md.web.user.auth.defaults.ApiResourcePermit;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class MRManager {

    private static final MRManager me = new MRManager();

    @Getter
    private MRAuthIntercept mrAuthIntercept;

    /**
     * 注册多个资源访问器
     */
    @Deprecated
    private final List<MRLoader> mrLoaders = new ArrayList<>();

    @Deprecated
    private final Map<String, MResource> allMResource = new HashMap<>();

    /**
     * 资源、判定器映射表，指定资源要使用的判定器
     */
    private final Map<Class<? extends MResource>, MRPermit> resourcePermitMapping = new HashMap<>();

    /**
     * 判定器由{@link MResource}指定，便于不同资源指定不同的判定器
     */
    @Deprecated
    @Setter
    private MRPermit permit;

    private MRManager() {
        // 内置
        configResourcePermitMapping(MetaApiResource.class, new ApiResourcePermit()); // 基于数据的接口资源判定
        configResourcePermitMapping(AnnotateApiResource.class, new AuthorizePermit()); // 基于注解的接口资源判定
    }

    public static MRManager me() {
        if (me.mrAuthIntercept == null) {
            Map<String, MRAuthInterceptDoer> map = AnalysisSpringUtil.getBeansOfTypes(MRAuthInterceptDoer.class);
            me.mrAuthIntercept = new MRAuthIntercept(map.values());
        }
        return me;
    }

    @Deprecated
    public void addLoader(MRLoader loader) {
        mrLoaders.add(loader);
    }

    @Deprecated
    public void load() {
        for (MRLoader m : mrLoaders) {
            m.load();
            for (MResource r : m.resources()) {
                allMResource.put(r.mResourceId(), r);
            }
        }
    }

    @Deprecated
    public List<MResource> resources() {
        return Lists.newArrayList(allMResource.values());
    }

    @Deprecated
    public Map<String, MResource> resourcesMap() {
        return allMResource;
    }

    @Deprecated
    public MResource getResource(String resourceKey) {
        return allMResource.get(resourceKey);
    }

    /**
     * 配置不同的资源，应当使用什么权限判定器来判定。
     *
     * @param clazz  资源类的Class
     * @param permit 权限判定器
     */
    public void configResourcePermitMapping(Class<? extends MResource> clazz, MRPermit permit) {
        this.resourcePermitMapping.put(clazz, permit);
    }

    public boolean permit(User user, MResource mResource) {
        if (mResource == null || !mResource.needPermit()) { // 直接放行的资源无需鉴权
            return true;
        }

        // 此资源需要鉴权，必定需要用户登录
        AssertUtil.isTrue(user != null, new UnLoginException("未认证"));

        for (Map.Entry<Class<? extends MResource>, MRPermit> entry : resourcePermitMapping.entrySet()) {
            if (entry.getKey().isInstance(mResource)) {
                return entry.getValue().permit(user, mResource);
            }
        }
        throw new MRException("资源%s无法确定应该使用什么判定器进行判定, 请在%s中指定",
                mResource.getClass().toString(), this.getClass().toString());
    }

}
