package com.hthjsj;

import com.hthjsj.analysis.db.registry.DataSourceManager;
import com.hthjsj.analysis.db.registry.JFinalActiveRecordPluginManager;
import com.hthjsj.analysis.meta.ConfigExtension;
import com.hthjsj.analysis.meta.MetaConfigFactory;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <p> @Date : 2019-08-20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@Configuration
public class SpringAnalysisManager {

    private static SpringAnalysisManager me;

    private final DataSourceManager dataSourceManager;

    private final JFinalActiveRecordPluginManager jFinalActiveRecordPluginManager;

    public SpringAnalysisManager(DataSourceManager dataSourceManager, JFinalActiveRecordPluginManager jFinalActiveRecordPluginManager) {
        this.dataSourceManager = dataSourceManager;
        this.jFinalActiveRecordPluginManager = jFinalActiveRecordPluginManager;
    }

    public static SpringAnalysisManager me() {
        return me;
    }

    public DbPro dbMain() {
        return Db.use(dataSourceManager.mainSource().schemaName());
    }

    @PostConstruct
    private void init() {
        me = this;
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
