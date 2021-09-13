package com.github.md.web.user.auth;

import lombok.Getter;
import lombok.Setter;

/**
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class MResourceGather implements MResource {

    @Getter
    @Setter
    MResource[] children;

    public boolean hasChildren() {
        return children != null && children.length > 0;
    }

    public abstract void add(MResource mResource);

    public abstract MResource get(String idOrName);

    public abstract void remove(MResource mResource);
}
