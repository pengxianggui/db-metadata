package com.hthjsj.web.config;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.web.component.Components;
import com.hthjsj.web.config.json.JsonParameterToMapHandler;
import com.hthjsj.web.kit.Dicts;
import com.hthjsj.web.kit.InitKit;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * Specially perform some initialization actions
 *
 * <p> @Date : 2021/9/8 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaBootstrap {

    MetaProperties metaProperties;

    MetaBootstrap(MetaProperties metaProperties) {
        this.metaProperties = metaProperties;
        init();
    }
    private void init(){

        Dicts.me().init();

        if (metaProperties.getServer().getMetaObject().isReplaceFromJsonFile()) {
            InitKit.me().updateMetaObjectConfig().updateInstanceConfig();
        }
        if(metaProperties.getServer().getComponent().isReplaceFromJsonFile()){
            Components.me().init();
            Components.me().addAutoInitComponents(ComponentType.SEARCHVIEW)
                      .addAutoInitComponents(ComponentType.TABLEVIEW)
                      .addAutoInitComponents(ComponentType.TABLETREEVIEW)
                      .addAutoInitComponents(ComponentType.FORMVIEW);
        }

    }
}
