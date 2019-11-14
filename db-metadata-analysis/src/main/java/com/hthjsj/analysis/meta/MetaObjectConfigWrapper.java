package com.hthjsj.analysis.meta;

/**
 * <p> @Date : 2019/11/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaObjectConfigWrapper extends MetaConfigFactory.MetaObjectConfig {

    public MetaObjectConfigWrapper(String config, String objectCode) {
        super(config, objectCode);
    }

    public MetaObjectConfigWrapper(String objectCode) {
        super(objectCode);
    }
}
