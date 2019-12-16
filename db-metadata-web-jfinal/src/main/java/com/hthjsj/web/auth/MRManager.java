package com.hthjsj.web.auth;

import com.hthjsj.web.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MRManager implements MRLoader, MRPermit {

    private static final MRManager me = new MRManager();

    /**
     * 注册多个资源访问器
     */
    private List<MRLoader> mrLoaders = new ArrayList<>();

    private MRPermit permit;

    private List<MResource> allMResource = new ArrayList<>();

    private MRManager() {

    }

    public MRManager(MRLoader loader, MRPermit permit) {
        me.mrLoaders.add(loader);
        me.permit = permit;
        me.load();
    }

    public static MRManager me() {
        return me;
    }

    @Override
    public void load() {
        mrLoaders.forEach(m -> {
            m.load();
            allMResource.addAll(m.resources());
        });
    }

    @Override
    public List<MResource> resources() {
        return allMResource;
    }

    @Override
    public boolean permit(User user, MResource mResource) {
        return permit.permit(user, mResource);
    }
}
