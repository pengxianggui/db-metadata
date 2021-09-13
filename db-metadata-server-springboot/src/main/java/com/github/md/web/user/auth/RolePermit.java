package com.github.md.web.user.auth;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class RolePermit implements MRPermit<MRRole, Object> {

    @Override
    public boolean permit(MRRole resourceHolder, Object mResource) {
        return false;
    }
}
