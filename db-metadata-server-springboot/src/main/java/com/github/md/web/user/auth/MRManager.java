package com.github.md.web.user.auth;

import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.web.user.User;
import com.github.md.web.user.UserException;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MRManager {

    private static final MRManager me = new MRManager();

    @Getter
    private MRAuthIntercept mrAuthIntercept;

    /**
     * 注册多个资源访问器
     */
    private final List<MRLoader> mrLoaders = new ArrayList<>();

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
        resourcePermitMapping.put(MetaAuthResource.class, new MetaAuthPermit());
    }

    public static MRManager me() {
        if (me.mrAuthIntercept == null) {
            Map<String, MRAuthInterceptDoer> map = AnalysisSpringUtil.getBeansOfTypes(MRAuthInterceptDoer.class);
            me.mrAuthIntercept = new MRAuthIntercept(map.values());
        }
        return me;
    }

    public void addLoader(MRLoader loader) {
        mrLoaders.add(loader);
    }

    public void load() {
        for (MRLoader m : mrLoaders) {
            m.load();
            for (MResource r : m.resources()) {
                allMResource.put(r.mResourceId(), r);
            }
        }
    }

    public List<MResource> resources() {
        return Lists.newArrayList(allMResource.values());
    }

    public Map<String, MResource> resourcesMap() {
        return allMResource;
    }

    public MResource getResource(String resourceKey) {
        return allMResource.get(resourceKey);
    }

    public boolean permit(User user, MResource mResource) {
        if (!mResource.needPermit()) {
            return true;
        }

        if (Objects.isNull(user)) {
            throw new UserException("无用户信息!");
        }

        for (Map.Entry<Class<? extends MResource>, MRPermit> entry : resourcePermitMapping.entrySet()) {
            if (entry.getKey().isInstance(mResource)) {
                return entry.getValue().permit(user, mResource);
            }
        }
        throw new MRException("资源%s无法确定应该使用什么判定器进行判定, 请在%s中指定",
                mResource.getClass().toString(), this.getClass().toString());
    }

}
