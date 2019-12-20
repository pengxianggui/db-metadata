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
}
