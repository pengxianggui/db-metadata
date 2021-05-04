package com.dbmeta.starter.config;

import com.hthjsj.AnalysisManager;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.web.AppConst;
import com.hthjsj.web.ExtJFinalConfig;
import com.hthjsj.web.component.Components;
import com.hthjsj.web.kit.InitKit;
import com.hthjsj.web.ui.ComputeKit;
import com.hthjsj.web.ui.meta.CCUUConfigExtension;
import com.hthjsj.web.ui.meta.InstanceConfigExtension;
import com.hthjsj.web.ui.meta.MetaFieldConfigExtension;

/**
 * @author pengxg
 * 2021/1/27 2:13 下午
 */
public class ComponentConfig extends ExtJFinalConfig {

    @Override
    public void onStart() {
        if (AppConst.getProp().getBoolean(AppConst.COMPONENT_ALLOW_REPLACE)) {
            //component register
            Components.me().init();
            Components.me()
                    .addAutoInitComponents(ComponentType.SEARCHVIEW)
                    .addAutoInitComponents(ComponentType.TABLEVIEW)
                    .addAutoInitComponents(ComponentType.FORMVIEW);
        }
        //Auto import anyConfig from json file;
        if (AppConst.getProp().getBoolean(AppConst.CONFIG_ALLOW_REPLACE)) {
            InitKit.me().updateMetaObjectConfig().updateInstanceConfig();
        }
        AnalysisManager.me().addMetaFieldConfigExtension(new MetaFieldConfigExtension());
        ComputeKit.addInstanceExtension(new InstanceConfigExtension());
        ComputeKit.addInstanceExtension(new CCUUConfigExtension());
    }
}
