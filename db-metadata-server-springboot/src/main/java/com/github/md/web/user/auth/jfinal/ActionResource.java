package com.github.md.web.user.auth.jfinal;

import com.github.md.web.user.auth.MResource;

/**
 * JFinal接口资源
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Deprecated
public class ActionResource implements MResource {

    private final String rid;

    private final String rName;

    private final boolean needPermit;

    public ActionResource(String rid, String rName, boolean needPermit) {
        this.rid = rid;
        this.rName = rName;
        this.needPermit = needPermit;
    }

    @Override
    public String mResourceId() {
        return rid;
    }

    @Override
    public String mResourceName() {
        return rName;
    }

    @Override
    public boolean needPermit() {
        return needPermit;
    }
}
