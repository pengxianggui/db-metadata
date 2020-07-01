package com.hthjsj.web.auth;

import com.google.common.collect.Lists;
import com.hthjsj.web.user.User;
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

    private MRManager() {
    }

    /**
     * 注册多个资源访问器
     */
    private List<MRLoader> mrLoaders = new ArrayList<>();

    @Setter
    private MRPermit permit;

    private Map<String, MResource> allMResource = new HashMap<>();

    public static MRManager me() {
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
