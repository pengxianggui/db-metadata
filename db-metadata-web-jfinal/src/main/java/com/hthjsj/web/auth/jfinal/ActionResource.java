package com.hthjsj.web.auth.jfinal;

import com.hthjsj.web.auth.MResource;

/**
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ActionResource implements MResource {

    private String rid;

    private String rName;

    private boolean needPermit;

    public ActionResource(String rid, String rName, boolean needPermit) {
        this.rid = rid;
        this.rName = rName;
        this.needPermit = needPermit;
    }

    @Override
    public String mResourceId() {
        return null;
    }

    @Override
    public String mResourceName() {
        return null;
    }

    @Override
    public boolean needPermit() {
        return false;
    }
}
