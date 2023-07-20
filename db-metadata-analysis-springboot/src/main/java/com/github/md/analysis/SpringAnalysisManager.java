package com.github.md.analysis;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.db.registry.DataSourceManager;
import com.github.md.analysis.db.registry.JFinalActiveRecordPluginManager;
import com.github.md.analysis.meta.*;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;
import lombok.Getter;
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

    @Getter
    private final DataSourceManager dataSourceManager;

    @Getter
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

    public void addMetaFieldConfigExtension(ConfigExtension<IMetaField, MetaFieldConfigParse, ComponentType> extension) {
        MetaConfigFactory.addFieldExtension(extension);
    }

    public List<ConfigExtension<IMetaField, MetaFieldConfigParse, ComponentType>> getMetaFieldConfigExtension() {
        return MetaConfigFactory.getFieldExtensions();
    }

    public void addMetaObjectConfigExtension(ConfigExtension<IMetaObject, MetaObjectConfigParse, ComponentType> extension) {
        MetaConfigFactory.addObjectExtension(extension);
    }

    public List<ConfigExtension<IMetaObject, MetaObjectConfigParse, ComponentType>> getMetaObjectConfigExtension() {
        return MetaConfigFactory.getObjectExtensions();
    }
}
