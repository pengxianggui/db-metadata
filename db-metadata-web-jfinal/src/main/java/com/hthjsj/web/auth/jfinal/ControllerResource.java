package com.hthjsj.web.auth.jfinal;

import com.hthjsj.web.auth.MResource;

/**
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ControllerResource implements MResource {

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
