package com.github.md.web.user.auth;

import com.github.md.web.user.User;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MRManager implements MRLoader, MRPermit<User, MResource> {

    private static final MRManager me = new MRManager();

    @Getter
    private MRAuthIntercept mrAuthIntercept;

    /**
     * 注册多个资源访问器
     */
    private final List<MRLoader> mrLoaders = new ArrayList<>();

    private final Map<String, MResource> allMResource = new HashMap<>();

    @Setter
    private MRPermit permit;

    private MRManager() {
    }

    public static MRManager me() {
        if (me.mrAuthIntercept == null) {
            me.mrAuthIntercept = new MRAuthIntercept();
        }
        return me;
    }

    public void addLoader(MRLoader loader) {
        mrLoaders.add(loader);
    }

    @Override
    public void load() {
        for (MRLoader m : mrLoaders) {
            m.load();
            for (MResource r : m.resources()) {
                allMResource.put(r.mResourceId(), r);
            }
        }
    }

    @Override
    public List<MResource> resources() {
        return Lists.newArrayList(allMResource.values());
    }

    public Map<String, MResource> resourcesMap() {
        return allMResource;
    }

    public MResource getResource(String resourceKey) {
        return allMResource.get(resourceKey);
    }

    @Override
    public boolean permit(User user, MResource mResource) {
        return permit.permit(user, mResource);
    }
}
