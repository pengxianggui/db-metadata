package com.hthjsj.web.feature;

import com.hthjsj.analysis.meta.MetaData;

/**
 * <p> @Date : 2019/12/20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class FeatureConfig extends MetaData implements Feature {

    protected String icon() {
        return getStr("icon");
    }

    /**
     * 前端Component默认按照FeatureType.code来路由
     * 如果有单独的需求需要对通过hasRouter & componentName
     *
     * @return
     */
    public boolean hasRouter() {
        return getBoolean("hasRouter") == null ? false : getBoolean("hasRouter");
    }

    public String componentName() {
        return getStr("componentName");
    }
}
