package com.hthjsj;

import com.hthjsj.analysis.meta.ConfigExtension;
import com.hthjsj.analysis.meta.MetaConfigFactory;

import java.util.List;

/**
 * <p> @Date : 2019/12/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class AnalysisManager {

    private final static AnalysisManager me = new AnalysisManager();

    private AnalysisManager() {
    }

    public static AnalysisManager me() {
        return me;
    }

    public void addMetaFieldConfigExtension(ConfigExtension extension) {
        MetaConfigFactory.addFieldExtension(extension);
    }

    public List<ConfigExtension> getMetaFieldConfigExtension() {
        return MetaConfigFactory.getFieldExtensions();
    }

    public void addMetaObjectConfigExtension(ConfigExtension extension) {
        MetaConfigFactory.addObjectExtension(extension);
    }

    public List<ConfigExtension> getMetaObjectConfigExtension() {
        return MetaConfigFactory.getObjectExtensions();
    }
}

